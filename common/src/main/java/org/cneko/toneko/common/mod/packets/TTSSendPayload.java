package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record TTSSendPayload(String text) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "tts_send");
    public static TTSSendPayload read(FriendlyByteBuf buf) { return new TTSSendPayload(buf.readUtf(32767)); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(text, 32767); }
    public ResourceLocation id() { return ID; }
}
