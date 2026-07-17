package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record NekoPosePayload(Pose pose, String uuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "entity_set_pose");
    public static NekoPosePayload read(FriendlyByteBuf buf) { return new NekoPosePayload(buf.readEnum(Pose.class), buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeEnum(pose).writeUtf(uuid); }
    public ResourceLocation id() { return ID; }
}
