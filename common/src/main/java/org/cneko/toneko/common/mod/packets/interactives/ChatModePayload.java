package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record ChatModePayload(boolean area) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "chat_mode");
    public static ChatModePayload read(FriendlyByteBuf buf) { return new ChatModePayload(buf.readBoolean()); }
    public void write(FriendlyByteBuf buf) { buf.writeBoolean(area); }
    public ResourceLocation id() { return ID; }
}
