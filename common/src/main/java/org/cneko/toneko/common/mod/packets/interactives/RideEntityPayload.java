package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record RideEntityPayload(String uuid, String vehicleUuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "neko_entity_interactive_ride_entity");
    public static RideEntityPayload read(FriendlyByteBuf buf) { return new RideEntityPayload(buf.readUtf(), buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid).writeUtf(vehicleUuid); }
    public ResourceLocation id() { return ID; }
}
