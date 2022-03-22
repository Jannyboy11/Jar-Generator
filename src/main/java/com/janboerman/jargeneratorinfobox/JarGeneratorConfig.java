package com.janboerman.jargeneratorinfobox;

import net.runelite.client.config.*;

@ConfigGroup("Jar Generator")
public interface JarGeneratorConfig extends Config {

    @ConfigItem(keyName = "charges", name = "", description = "", hidden = true)
    public default int charges() {
        return 0;
    }

    @ConfigItem(keyName = "charges", name = "", description = "")
    public void charges(int charges);

}
