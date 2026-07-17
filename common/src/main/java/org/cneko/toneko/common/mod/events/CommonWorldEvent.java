package org.cneko.toneko.common.mod.events;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import org.cneko.toneko.common.mod.entities.INeko;
import org.cneko.toneko.common.mod.entities.NekoAccess;
import org.cneko.toneko.common.mod.quirks.ModQuirk;

public class CommonWorldEvent {
    public static void onWorldUnLoad(MinecraftServer minecraftServer, ServerLevel serverLevel) {
    }

    public static void onWeatherChange(ServerLevel serverLevel, int clearTime, int weatherTime, boolean isRaining, boolean isThundering) {
        var players = serverLevel.players();
        for (var player : players){
            INeko neko = NekoAccess.require(player);
            for (var quirk: neko.getQuirks()){
                ModQuirk mq = quirk;
                mq.onWeatherChange(neko,serverLevel,clearTime,weatherTime,isRaining,isThundering);
            }
        }
    }
}
