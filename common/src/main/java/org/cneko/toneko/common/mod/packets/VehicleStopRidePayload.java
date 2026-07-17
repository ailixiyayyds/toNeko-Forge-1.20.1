package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record VehicleStopRidePayload(String uuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "vehicle_stop_ride");
    public static VehicleStopRidePayload read(FriendlyByteBuf buf) { return new VehicleStopRidePayload(buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid); }
    public ResourceLocation id() { return ID; }
}
