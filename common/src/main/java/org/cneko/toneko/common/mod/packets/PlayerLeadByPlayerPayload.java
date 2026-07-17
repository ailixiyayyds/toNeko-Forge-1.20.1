package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record PlayerLeadByPlayerPayload(String holder, String target) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "player_lead_by_player");
    public static PlayerLeadByPlayerPayload read(FriendlyByteBuf buf) { return new PlayerLeadByPlayerPayload(buf.readUtf(), buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(holder).writeUtf(target); }
    public ResourceLocation id() { return ID; }
}
