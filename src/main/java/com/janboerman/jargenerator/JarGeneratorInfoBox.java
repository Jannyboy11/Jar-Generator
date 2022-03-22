package com.janboerman.jargenerator;

import net.runelite.client.plugins.Plugin;
import net.runelite.client.ui.overlay.infobox.Counter;

import java.awt.image.BufferedImage;

public final class JarGeneratorInfoBox extends Counter {

    public JarGeneratorInfoBox(BufferedImage image, Plugin plugin, int charges) {
        super(image, plugin, charges);
    }

    public int getCharges() {
        return getCount();
    }

    public void setCharges(int charges) {
        setCount(charges);
    }

}
