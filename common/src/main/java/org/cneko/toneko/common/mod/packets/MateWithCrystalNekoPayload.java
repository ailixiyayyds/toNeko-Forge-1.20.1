package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record MateWithCrystalNekoPayload(String uuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "mate_with_crystal_neko");
    public static MateWithCrystalNekoPayload read(FriendlyByteBuf buf) { return new MateWithCrystalNekoPayload(buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid); }
    public ResourceLocation id() { return ID; }
}
