package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record CrystalNekoNyaPayload(String uuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "crystal_neko_nya");
    public static CrystalNekoNyaPayload read(FriendlyByteBuf buf) { return new CrystalNekoNyaPayload(buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid); }
    public ResourceLocation id() { return ID; }
}
