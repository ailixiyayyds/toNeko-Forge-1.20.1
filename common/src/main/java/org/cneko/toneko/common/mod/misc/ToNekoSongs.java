package org.cneko.toneko.common.mod.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import static org.cneko.toneko.common.Bootstrap.MODID;

/** 1.20.1 music discs reference sound events directly. */
public final class ToNekoSongs {
    public static final SoundEvent KAWAII = SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "music.kawaii"));
    public static final SoundEvent NEVER_GONNA_GIVE_YOU_UP = SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, "music.never_gonna_give_you_up"));

    private ToNekoSongs() {
    }
}
