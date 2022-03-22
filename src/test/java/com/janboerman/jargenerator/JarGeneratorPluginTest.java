package com.janboerman.jargenerator;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class JarGeneratorPluginTest  {

	public static void main(String[] args) throws Exception {
		ExternalPluginManager.loadBuiltin(JarGeneratorPlugin.class);
		RuneLite.main(args);
	}

}