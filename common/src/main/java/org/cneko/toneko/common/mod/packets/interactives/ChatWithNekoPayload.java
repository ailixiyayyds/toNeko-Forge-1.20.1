package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record ChatWithNekoPayload(String uuid, String message) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "chat_with_neko_entity");
    public static ChatWithNekoPayload read(FriendlyByteBuf buf) { return new ChatWithNekoPayload(buf.readUtf(), buf.readUtf(32767)); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid).writeUtf(message, 32767); }
    public ResourceLocation id() { return ID; }
}
