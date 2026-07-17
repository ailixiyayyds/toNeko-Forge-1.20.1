package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record ChatHistoryRequestPayload(String nekoUuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "chat_history_request");
    public static ChatHistoryRequestPayload read(FriendlyByteBuf buf) { return new ChatHistoryRequestPayload(buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(nekoUuid); }
    public ResourceLocation id() { return ID; }
}
