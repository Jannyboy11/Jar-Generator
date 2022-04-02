package com.janboerman.jargenerator;

import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.ui.overlay.WidgetItemOverlay;
import static com.janboerman.jargenerator.JarGeneratorPlugin.JAR_GENERATOR_ITEM_ID;
import net.runelite.client.ui.overlay.components.TextComponent;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Point;
import java.awt.Color;

public class JarGeneratorOverlay extends WidgetItemOverlay {

    private final JarGeneratorPlugin plugin;
    private final JarGeneratorConfig config;

    public JarGeneratorOverlay(JarGeneratorPlugin plugin, JarGeneratorConfig config) {
        this.plugin = plugin;
        this.config = config;

        showOnInventory();
    }

    @Override
    public void renderItemOverlay(Graphics2D graphics, int itemId, WidgetItem itemWidget) {
        if (itemId == JAR_GENERATOR_ITEM_ID) {
            final Rectangle bounds = itemWidget.getCanvasBounds();
            final TextComponent textComponent = new TextComponent();
            textComponent.setPosition(new Point(bounds.x - 1, bounds.y + 8));

            final Charges charges = plugin.getCharges();

            final Color colour;
            final String text;

            if (charges instanceof Unknown) {
                colour = config.unknownChargesColour();
                text = "?"; // Don't call charges.getAmount(), because that will throw.
            } else {
                colour = charges.isKnown() ? config.knownChargesColour() : config.unknownChargesColour();
                text = String.valueOf(charges.getAmount());
            }

            textComponent.setColor(colour);
            textComponent.setText(text);
            textComponent.render(graphics);
        }
    }

}
