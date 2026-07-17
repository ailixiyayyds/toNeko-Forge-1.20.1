package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record OpenNekoInfoScreenPayload() implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "open_neko_info_screen");
    public static OpenNekoInfoScreenPayload read(FriendlyByteBuf buf) { return new OpenNekoInfoScreenPayload(); }
    public void write(FriendlyByteBuf buf) { }
    public ResourceLocation id() { return ID; }
}
