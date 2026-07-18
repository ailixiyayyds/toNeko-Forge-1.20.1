package org.cneko.toneko.forge;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.cneko.toneko.common.Bootstrap;
import org.cneko.toneko.common.mod.ModBootstrap;
import org.cneko.toneko.common.mod.ModMeta;
import org.cneko.toneko.common.mod.commands.GeneticsCommand;
import org.cneko.toneko.common.mod.commands.NekoCommand;
import org.cneko.toneko.common.mod.commands.QuirkCommand;
import org.cneko.toneko.common.mod.commands.ToNekoAdminCommand;
import org.cneko.toneko.common.mod.commands.ToNekoCommand;
import org.cneko.toneko.common.mod.events.ToNekoEvents;
import org.cneko.toneko.common.mod.events.ToNekoNetworkEvents;
import org.cneko.toneko.common.mod.genetics.api.GeneticsDataLoader;
import org.cneko.toneko.common.mod.impl.FabricLanguageImpl;
import org.cneko.toneko.common.mod.packets.ToNekoPackets;
import org.cneko.toneko.common.mod.quirks.ToNekoQuirks;
import org.cneko.toneko.common.mod.util.PermissionUtil;
import org.cneko.toneko.common.util.LanguageUtil;

/**
 * Native Forge entry point for the 1.20.1 port.
 *
 * Platform registrations are migrated here incrementally from the retained
 * NeoForge reference module. Forgified Fabric API temporarily implements the
 * remaining Fabric callbacks; Forge owns the actual mod lifecycle.
 */
@Mod(ToNekoForge.MOD_ID)
public final class ToNekoForge {
    public static final String MOD_ID = "toneko";

    public ToNekoForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ToNekoForgeContent.register(modBus);
        ToNekoForgeContent.registerCriteria();
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, ToNekoForgePlayerCapability::attach);
        MinecraftForge.EVENT_BUS.addListener(ToNekoForgePlayerCapability::copyOnClone);

        LanguageUtil.INSTANCE = new FabricLanguageImpl();
        Bootstrap.bootstrap();
        ModBootstrap.bootstrap();

        ToNekoCommand.init();
        ToNekoAdminCommand.init();
        NekoCommand.init();
        QuirkCommand.init();
        GeneticsCommand.init();

        ToNekoQuirks.init();
        ToNekoPackets.init();

        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(new GeneticsDataLoader());

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ModMeta.INSTANCE.setServer(server);
            ToNekoEvents.init();
            ToNekoNetworkEvents.init();
            PermissionUtil.init();
        });
    }
}
