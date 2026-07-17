package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/** A small packet contract compatible with Fabric API on Minecraft 1.20.1. */
public interface ToNekoPayload {
    ResourceLocation id();

    void write(FriendlyByteBuf buffer);
}
