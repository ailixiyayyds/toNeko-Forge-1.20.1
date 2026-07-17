package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.mod.util.ResourceLocationUtil.toNekoLoc;

public record ToNekoActionPayload(String action, String targetUuid, String value1, String value2, String value3)
        implements ToNekoPayload {
    public static final ResourceLocation ID = toNekoLoc("toneko_action");
    public static ToNekoActionPayload read(FriendlyByteBuf buf) {
        return new ToNekoActionPayload(buf.readUtf(), buf.readUtf(), buf.readUtf(), buf.readUtf(), buf.readUtf());
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(action).writeUtf(targetUuid).writeUtf(value1).writeUtf(value2).writeUtf(value3);
    }
    public ResourceLocation id() { return ID; }
}
