package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record NekoInfoSyncPayload(float energy, float maxEnergy, double interactionRaw,
                                  double combatRaw, double baseRaw, boolean isNeko, int age)
        implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "neko_info_sync");
    public static NekoInfoSyncPayload read(FriendlyByteBuf buf) {
        return new NekoInfoSyncPayload(buf.readFloat(), buf.readFloat(), buf.readDouble(),
                buf.readDouble(), buf.readDouble(), buf.readBoolean(), buf.readInt());
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(energy).writeFloat(maxEnergy).writeDouble(interactionRaw)
                .writeDouble(combatRaw).writeDouble(baseRaw).writeBoolean(isNeko).writeInt(age);
    }
    public ResourceLocation id() { return ID; }
}
