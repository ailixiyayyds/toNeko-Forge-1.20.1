package org.cneko.toneko.common.mod.packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.mod.util.ResourceLocationUtil.toNekoLoc;

public record ToNekoManagementDataPayload(CompoundTag data) implements ToNekoPayload {
    public static final ResourceLocation ID = toNekoLoc("toneko_management_data");
    public static ToNekoManagementDataPayload read(FriendlyByteBuf buf) { return new ToNekoManagementDataPayload(buf.readNbt()); }
    public void write(FriendlyByteBuf buf) { buf.writeNbt(data); }
    public ResourceLocation id() { return ID; }
}
