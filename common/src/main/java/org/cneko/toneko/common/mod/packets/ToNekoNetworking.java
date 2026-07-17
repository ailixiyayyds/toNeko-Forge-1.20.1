package org.cneko.toneko.common.mod.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;
import java.util.function.Function;

public final class ToNekoNetworking {
    private ToNekoNetworking() {
    }

    public record ServerContext(MinecraftServer server, ServerPlayer player) {
    }

    public static void send(ServerPlayer player, ToNekoPayload payload) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        payload.write(buffer);
        ServerPlayNetworking.send(player, payload.id(), buffer);
    }

    public static <T extends ToNekoPayload> void registerC2S(
            ResourceLocation id,
            Function<FriendlyByteBuf, T> decoder,
            BiConsumer<T, ServerContext> receiver) {
        ServerPlayNetworking.registerGlobalReceiver(id, (server, player, handler, buffer, responseSender) ->
                receiver.accept(decoder.apply(buffer), new ServerContext(server, player)));
    }
}
