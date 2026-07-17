package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record GiftItemPayload(String uuid, int slot) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "neko_entity_interactive_gift_item");
    public static GiftItemPayload read(FriendlyByteBuf buf) { return new GiftItemPayload(buf.readUtf(), buf.readInt()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid).writeInt(slot); }
    public ResourceLocation id() { return ID; }
}
