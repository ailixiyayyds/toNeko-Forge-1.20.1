package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record PluginDetectPayload(String installed) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "detect");
    public static PluginDetectPayload read(FriendlyByteBuf buf) { return new PluginDetectPayload(buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(installed); }
    public ResourceLocation id() { return ID; }
}
