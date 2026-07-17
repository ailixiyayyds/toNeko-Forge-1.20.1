package org.cneko.toneko.common.mod.packets;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import static org.cneko.toneko.common.Bootstrap.MODID;

public record OpenPlotScreenPayload() implements ToNekoPayload {
    public static final ResourceLocation ID = new ResourceLocation(MODID, "open_plot_screen");
    public static OpenPlotScreenPayload read(FriendlyByteBuf buf) { return new OpenPlotScreenPayload(); }
    public void write(FriendlyByteBuf buf) { }
    public ResourceLocation id() { return ID; }
}
