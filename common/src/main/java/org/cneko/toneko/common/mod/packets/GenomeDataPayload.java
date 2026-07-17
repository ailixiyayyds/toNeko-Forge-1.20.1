package org.cneko.toneko.common.mod.packets;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.mod.util.ResourceLocationUtil.toNekoLoc;

public record GenomeDataPayload(int entityId, CompoundTag genomeNbt, boolean canEdit) implements ToNekoPayload {
    public static final ResourceLocation ID = toNekoLoc("genome_data");
    public static GenomeDataPayload read(FriendlyByteBuf buf) {
        return new GenomeDataPayload(buf.readInt(), buf.readNbt(), buf.readBoolean());
    }
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeNbt(genomeNbt);
        buf.writeBoolean(canEdit);
    }
    public ResourceLocation id() { return ID; }
}
