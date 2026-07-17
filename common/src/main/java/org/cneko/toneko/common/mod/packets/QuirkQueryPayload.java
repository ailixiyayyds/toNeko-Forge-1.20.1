package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record QuirkQueryPayload(List<String> quirks, List<String> allQuirks, boolean openScreen)
        implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "quirk_query");
    public static QuirkQueryPayload read(FriendlyByteBuf buf) {
        return new QuirkQueryPayload(readStrings(buf), readStrings(buf), buf.readBoolean());
    }
    private static List<String> readStrings(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        List<String> values = new ArrayList<>(size);
        for (int i = 0; i < size; i++) values.add(buf.readUtf());
        return values;
    }
    private static void writeStrings(FriendlyByteBuf buf, List<String> values) {
        buf.writeVarInt(values.size());
        values.forEach(buf::writeUtf);
    }
    public void write(FriendlyByteBuf buf) {
        writeStrings(buf, quirks);
        writeStrings(buf, allQuirks);
        buf.writeBoolean(openScreen);
    }
    public List<String> getQuirks() { return quirks; }
    public List<String> getAllQuirks() { return allQuirks; }
    public boolean isOpenScreen() { return openScreen; }
    public ResourceLocation id() { return ID; }
}
