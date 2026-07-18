package org.cneko.toneko.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.cneko.toneko.common.mod.client.events.ClientTickEvent;

/**
 * Native Forge client-loop bridge.
 *
 * Key actions and client task processing must not depend on Forgified Fabric
 * lifecycle callbacks: those callbacks are not guaranteed to run on every
 * Forge launch configuration.
 */
@Mod.EventBusSubscriber(
        modid = ToNekoForge.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE,
        value = Dist.CLIENT
)
public final class ToNekoForgeClientEvents {
    private ToNekoForgeClientEvents() {
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        Minecraft client = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.START) {
            ClientTickEvent.onTick(client);
        } else {
            ClientTickEvent.processKeyInput(client);
        }
    }
}
