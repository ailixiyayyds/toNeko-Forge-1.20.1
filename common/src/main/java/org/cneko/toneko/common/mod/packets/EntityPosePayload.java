package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record EntityPosePayload(Pose pose, String uuid, boolean status) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "entity_set_pose");
    public static EntityPosePayload read(FriendlyByteBuf buf) {
        return new EntityPosePayload(buf.readEnum(Pose.class), buf.readUtf(), buf.readBoolean());
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeEnum(pose).writeUtf(uuid == null ? "" : uuid).writeBoolean(status);
    }
    public ResourceLocation id() { return ID; }
}
