package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record NekoMatePayload(String uuid, String mateUuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "neko_entity_interactive_mate");
    public static NekoMatePayload read(FriendlyByteBuf buf) { return new NekoMatePayload(buf.readUtf(), buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid).writeUtf(mateUuid); }
    public ResourceLocation id() { return ID; }
}
