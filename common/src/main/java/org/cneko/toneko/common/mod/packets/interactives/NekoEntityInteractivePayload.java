package org.cneko.toneko.common.mod.packets.interactives;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record NekoEntityInteractivePayload(String uuid) implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "neko_entity_interactive");
    public static NekoEntityInteractivePayload read(FriendlyByteBuf buf) { return new NekoEntityInteractivePayload(buf.readUtf()); }
    public void write(FriendlyByteBuf buf) { buf.writeUtf(uuid); }
    public ResourceLocation id() { return ID; }
}
