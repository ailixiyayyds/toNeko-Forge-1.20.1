package org.cneko.toneko.common.mod.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import org.cneko.toneko.common.api.TickTasks;
import org.cneko.toneko.common.mod.advencements.ToNekoCriteria;
import org.cneko.toneko.common.mod.entities.INeko;
import org.cneko.toneko.common.mod.entities.NekoAccess;
import org.cneko.toneko.common.mod.quirks.ModQuirk;
import org.cneko.toneko.common.mod.quirks.Quirk;

public class CommonPlayerEvent {
    private static final int SLOW_TICK_TIMES = 100;
    private static int tickTimes = 0;
    public static void startTick(MinecraftServer server) {
        TickTasks.executeDefault();
        if(tickTimes++ >= SLOW_TICK_TIMES){
            tickTimes = 0;
            // 触发进度
            for (ServerPlayer p : server.getPlayerList().getPlayers()) {
                ToNekoCriteria.NEKO_LV100.trigger(p);
            }
        }
    }

    public static void startSleep(LivingEntity entity, BlockPos pos) {
        if(entity instanceof ServerPlayer player){
            INeko neko = NekoAccess.require(player);
            for (Quirk quirk : neko.getQuirks()){
                ModQuirk modQuirk = quirk;
                modQuirk.startSleep(neko,pos);
            }
        }
    }

    public static void stopSleep(LivingEntity entity, BlockPos pos) {
        if(entity instanceof ServerPlayer player){
            INeko neko = NekoAccess.require(player);
            for (Quirk quirk : neko.getQuirks()){
                ModQuirk modQuirk = quirk;
                modQuirk.stopSleep(neko,pos);
            }
        }
    }
}
