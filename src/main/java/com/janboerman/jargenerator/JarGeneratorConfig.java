package com.janboerman.jargenerator;

import net.runelite.client.config.*;

import java.awt.Color;

@ConfigGroup("JarGenerator")
public interface JarGeneratorConfig extends Config {

    @ConfigItem(keyName = "charges", name = "", description = "", hidden = true)
    public default int charges() {
        return 0;
    }

    @ConfigItem(keyName = "charges", name = "", description = "")
    public void charges(int charges);

    @ConfigItem(keyName = "knownChargesColour", name = "Known colour", description = "Configures the text colour when the amount of charges is known exactly", position = 1)
    default Color knownChargesColour() {
        return Color.CYAN;
    }

    @ConfigItem(keyName = "unkownChargesColour", name = "Unknown colour", description = "Configures the text colour when the amount of charges is not exactly known", position = 2)
    default Color unknownChargesColour() {
        return new Color(0xFF, 0x40, 0x00);
    }

}
