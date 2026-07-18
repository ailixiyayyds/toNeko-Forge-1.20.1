package org.cneko.toneko.forge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.cneko.toneko.common.mod.blocks.ToNekoBlocks;
import org.cneko.toneko.common.mod.client.ToNekoKeyBindings;
import org.cneko.toneko.common.mod.client.events.ClientNetworkEvents;
import org.cneko.toneko.common.mod.client.events.ClientPlayerJoinEvent;
import org.cneko.toneko.common.mod.client.events.ClientTickEvent;
import org.cneko.toneko.common.mod.client.events.HudRenderEvent;
import org.cneko.toneko.common.mod.client.renderers.AmmunitionRenderer;
import org.cneko.toneko.common.mod.client.renderers.GhostNekoRenderer;
import org.cneko.toneko.common.mod.client.renderers.NekoBossRenderer;
import org.cneko.toneko.common.mod.client.renderers.NekoRenderer;
import org.cneko.toneko.common.mod.entities.ToNekoEntities;

@Mod.EventBusSubscriber(
        modid = ToNekoForge.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class ToNekoForgeClient {
    private ToNekoForgeClient() {
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ToNekoEntities.ADVENTURER_NEKO, NekoRenderer::new);
        event.registerEntityRenderer(ToNekoEntities.CRYSTAL_NEKO, NekoRenderer::new);
        event.registerEntityRenderer(ToNekoEntities.GHOST_NEKO, GhostNekoRenderer::new);
        event.registerEntityRenderer(ToNekoEntities.FIGHTING_NEKO, NekoRenderer::new);
        event.registerEntityRenderer(ToNekoEntities.AMMUNITION_ENTITY, AmmunitionRenderer::new);
        event.registerEntityRenderer(ToNekoEntities.MOUFLET_NEKO_BOSS, NekoBossRenderer::new);
        event.registerEntityRenderer(ToNekoEntities.RAVENN_ENTITY, NekoRenderer::new);
        event.registerEntityRenderer(ToNekoEntities.NOELLE_MAID_NEKO, NekoRenderer::new);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ToNekoKeyBindings.init();
            ClientNetworkEvents.init();
            ClientTickEvent.init();
            ClientPlayerJoinEvent.init();
            HudRenderEvent.init();
            ItemBlockRenderTypes.setRenderLayer(ToNekoBlocks.CATNIP, RenderType.cutout());
            org.cneko.toneko.common.mod.client.ToNekoClient.init();
        });
    }
}
