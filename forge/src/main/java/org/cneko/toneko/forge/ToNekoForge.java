package org.cneko.toneko.forge;

import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.cneko.toneko.common.Bootstrap;
import org.cneko.toneko.common.mod.ModBootstrap;
import org.cneko.toneko.common.mod.ModMeta;
import org.cneko.toneko.common.mod.impl.FabricLanguageImpl;
import org.cneko.toneko.common.mod.packets.ToNekoPackets;
import org.cneko.toneko.common.mod.quirks.ToNekoQuirks;
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

        ToNekoQuirks.init();
        ToNekoPackets.init();

    }
}
