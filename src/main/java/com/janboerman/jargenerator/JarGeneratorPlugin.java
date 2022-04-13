package com.janboerman.jargenerator;

import javax.inject.Inject;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.ItemID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Jar Generator",
	description = "Overlays jar generator charges",
	tags = {"impling", "butterfly", "jar", "generator", "charges"}
)
public class JarGeneratorPlugin extends Plugin
{
	static final int JAR_GENERATOR_ITEM_ID = ItemID.JAR_GENERATOR; // 11258

	private Charges currentCharges = Charges.unknown();
	private JarGeneratorOverlay jarGeneratorOverlay;

	@Inject private Client client;
	@Inject private OverlayManager overlayManager;
	@Inject private JarGeneratorConfig config;

	@Provides
	JarGeneratorConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(JarGeneratorConfig.class);
	}

	@Override
	protected void startUp() {
		log.info("Jar Generator started!");

		setCharges(Charges.probably(config.charges()));
		jarGeneratorOverlay = new JarGeneratorOverlay(this, config);
		overlayManager.add(jarGeneratorOverlay);
	}

	@Override
	protected void shutDown() {
		log.info("Jar Generator stopped!");

		overlayManager.remove(jarGeneratorOverlay);
		jarGeneratorOverlay = null;
		setCharges(Charges.probably(getCharges().getAmount()));
	}

	public Charges getCharges() {
		return currentCharges;
	}

	public void setCharges(Charges charges) {
		log.debug("Set charges to " + charges);
		this.currentCharges = charges;

		if (!(charges instanceof Unknown)) {
			this.config.charges(charges.getAmount());
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event) {
		if (event.getType() != ChatMessageType.GAMEMESSAGE) return;

		String text = event.getMessage();
		Charges charges = JarGeneratorLingo.interpret(text);
		if (charges.isKnown()) {
			setCharges(charges);
		}
	}

	private int lastCheckTick = -1;

	@Subscribe
	public void onScriptCallbackEvent(ScriptCallbackEvent event) {
		if ("destroyOnOpKey".equals(event.getEventName())) {

			int yesOption = client.getIntStack()[client.getIntStackSize() - 1];
			if (yesOption == 1) {

				final int currentTick = client.getTickCount();
				if (lastCheckTick != currentTick) {
					lastCheckTick = currentTick;

					final Widget widgetDestroyItemName = client.getWidget(WidgetInfo.DESTROY_ITEM_NAME);
					if (widgetDestroyItemName != null && widgetDestroyItemName.getText().equals("Jar generator")) {
						setCharges(Charges.empty());
					}
				}
			}
		}
	}

}
