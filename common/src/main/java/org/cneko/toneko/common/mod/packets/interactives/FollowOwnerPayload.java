package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record FollowOwnerPayload(String uuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "neko_entity_interactive_follow_owner");
    public static FollowOwnerPayload read(FriendlyByteBuf buf) { return new FollowOwnerPayload(buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid); }
    public ResourceLocation id() { return ID; }
}
