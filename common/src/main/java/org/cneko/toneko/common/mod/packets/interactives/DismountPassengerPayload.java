package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record DismountPassengerPayload() implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "dismount_passenger");
    public static DismountPassengerPayload read(FriendlyByteBuf buf) { return new DismountPassengerPayload(); }
    public void write(FriendlyByteBuf buf) { }
    public ResourceLocation id() { return ID; }
}
