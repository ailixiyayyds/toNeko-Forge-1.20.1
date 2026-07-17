package org.cneko.toneko.forge;

import net.minecraftforge.fml.common.Mod;

/**
 * Native Forge entry point for the 1.20.1 port.
 *
 * Platform registrations are migrated here incrementally from the retained
 * NeoForge reference module.  Keeping this entry point minimal prevents the
 * incomplete registry layer from exposing partially registered game content.
 */
@Mod(ToNekoForge.MOD_ID)
public final class ToNekoForge {
    public static final String MOD_ID = "toneko";

    public ToNekoForge() {
    }
}
