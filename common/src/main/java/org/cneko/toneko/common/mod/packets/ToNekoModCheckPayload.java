package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record ToNekoModCheckPayload(boolean status) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "mod_check");
    public static ToNekoModCheckPayload read(FriendlyByteBuf buf) { return new ToNekoModCheckPayload(buf.readBoolean()); }
    public void write(FriendlyByteBuf buf) { buf.writeBoolean(status); }
    public ResourceLocation id() { return ID; }
}
