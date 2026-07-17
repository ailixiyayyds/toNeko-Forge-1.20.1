package org.cneko.toneko.common.mod.client.events;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.packets.ToNekoPayload;

import java.util.function.BiConsumer;
import java.util.function.Function;

public final class ToNekoClientNetworking {
    private ToNekoClientNetworking() {
    }

    public record ClientContext(Minecraft client, LocalPlayer player) {
    }

    public static void send(ToNekoPayload payload) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        payload.write(buffer);
        ClientPlayNetworking.send(payload.id(), buffer);
    }

    public static <T extends ToNekoPayload> void registerS2C(
            ResourceLocation id,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, ClientContext> receiver) {
        ClientPlayNetworking.registerGlobalReceiver(id, (client, handler, buffer, responseSender) ->
                receiver.accept(decoder.apply(buffer), new ClientContext(client, client.player)));
    }
}
