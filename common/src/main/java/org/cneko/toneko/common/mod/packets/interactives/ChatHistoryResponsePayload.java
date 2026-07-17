package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import java.util.ArrayList;
import java.util.List;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record ChatHistoryResponsePayload(String nekoUuid, List<String> messages) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "chat_history_response");
    public static ChatHistoryResponsePayload read(FriendlyByteBuf buf) {
        String uuid = buf.readUtf();
        int size = buf.readVarInt();
        List<String> messages = new ArrayList<>(size);
        for (int i = 0; i < size; i++) messages.add(buf.readUtf(32767));
        return new ChatHistoryResponsePayload(uuid, messages);
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(nekoUuid).writeVarInt(messages.size());
        messages.forEach(message -> buf.writeUtf(message, 32767));
    }
    public ResourceLocation id() { return ID; }
}
