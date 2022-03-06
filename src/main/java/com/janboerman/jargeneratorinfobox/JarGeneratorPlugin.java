package com.janboerman.jargeneratorinfobox;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.ItemID;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.infobox.InfoBoxManager;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.OptionalInt;

@Slf4j
@PluginDescriptor(
	name = "Jar Generator"
)
public class JarGeneratorPlugin extends Plugin
{
	private static final int JAR_GENERATOR_ITEM_ID = ItemID.JAR_GENERATOR; //11258

	private BufferedImage jarGeneratorImage;
	private JarGeneratorInfoBox infoBox;

	@Inject private Client client;
	@Inject private InfoBoxManager infoBoxManager;

	@Override
	protected void startUp() throws Exception
	{
		log.info("Jar Generator started!");

		jarGeneratorImage = ImageUtil.loadImageResource(JarGeneratorPlugin.class, "/jar_generator.png");

		ItemContainer inventoryContainer = client.getItemContainer(InventoryID.INVENTORY);
		if (inventoryContainer != null && inventoryContainer.contains(JAR_GENERATOR_ITEM_ID)) {
			infoBox = new JarGeneratorInfoBox(jarGeneratorImage, this, 0);
			infoBoxManager.addInfoBox(infoBox);
		}
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Jar Generator stopped!");

		if (infoBox != null) {
			infoBoxManager.removeInfoBox(infoBox);
		}

		jarGeneratorImage = null;
	}

	@Subscribe
	public void onChatMessage(ChatMessage event) {
		if (event.getType() != ChatMessageType.GAMEMESSAGE) return;

		String text = event.getMessage();
		OptionalInt charges = JarGeneratorLingo.interpret(text);
		if (charges.isPresent()) {
			int charge = charges.getAsInt();
			if (infoBox == null) {
				infoBox = new JarGeneratorInfoBox(jarGeneratorImage, this, charge);
				infoBoxManager.addInfoBox(infoBox);
			} else {
				infoBox.setCharges(charge);
			}
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged event) {
		ItemContainer itemContainer = event.getItemContainer();
		if (itemContainer.getId() == InventoryID.INVENTORY.getId()) {
			if (client.getItemContainer(InventoryID.INVENTORY).contains(JAR_GENERATOR_ITEM_ID)) {
				if (infoBox == null) {
					infoBox = new JarGeneratorInfoBox(jarGeneratorImage, this, 0);
					infoBoxManager.addInfoBox(infoBox);
				}
			} else {
				if (infoBox != null) {
					infoBoxManager.removeInfoBox(infoBox);
					infoBox = null;
				}
			}
		}
	}

}
