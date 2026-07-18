package org.cneko.toneko.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.cneko.toneko.common.mod.ModMeta;
import org.cneko.toneko.common.mod.api.NekoLevelRegistry;
import org.cneko.toneko.common.mod.commands.GeneticsCommand;
import org.cneko.toneko.common.mod.commands.NekoCommand;
import org.cneko.toneko.common.mod.commands.QuirkCommand;
import org.cneko.toneko.common.mod.commands.ToNekoAdminCommand;
import org.cneko.toneko.common.mod.commands.ToNekoCommand;
import org.cneko.toneko.common.mod.entities.INeko;
import org.cneko.toneko.common.mod.entities.NekoAccess;
import org.cneko.toneko.common.mod.events.CommonChatEvent;
import org.cneko.toneko.common.mod.events.CommonPlayerEvent;
import org.cneko.toneko.common.mod.events.CommonPlayerInteractionEvent;
import org.cneko.toneko.common.mod.events.CommonWorldEvent;
import org.cneko.toneko.common.mod.events.ToNekoNetworkEvents;
import org.cneko.toneko.common.mod.genetics.api.GeneticsDataLoader;
import org.cneko.toneko.common.mod.items.NekoEnergyBurstItem;
import org.cneko.toneko.common.mod.items.ToNekoItems;
import org.cneko.toneko.common.mod.quirks.ModQuirk;
import org.cneko.toneko.common.mod.util.PermissionUtil;
import org.cneko.toneko.common.util.ConfigUtil;

import java.util.HashMap;
import java.util.Map;

/** Native Forge registrations that belong to each server command dispatcher. */
@Mod.EventBusSubscriber(
        modid = ToNekoForge.MOD_ID,
        bus = Mod.EventBusSubscriber.Bus.FORGE
)
public final class ToNekoForgeServerEvents {
    private record WeatherState(boolean raining, boolean thundering) {
    }

    private static final Map<net.minecraft.resources.ResourceKey<Level>, WeatherState> WEATHER = new HashMap<>();

    private ToNekoForgeServerEvents() {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        ToNekoCommand.register(event.getDispatcher());
        ToNekoAdminCommand.register(event.getDispatcher());
        NekoCommand.register(event.getDispatcher());
        QuirkCommand.register(event.getDispatcher());
        GeneticsCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        ModMeta.INSTANCE.setServer(event.getServer());
        ToNekoNetworkEvents.init();
        PermissionUtil.init();
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new GeneticsDataLoader());
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        INeko neko = NekoAccess.require(player);
        if (!neko.isNeko()) return;
        neko.fixQuirks();
        for (var quirk : neko.getQuirks()) {
            ModQuirk modQuirk = quirk;
            modQuirk.onJoin(neko);
        }
    }

    @SubscribeEvent
    public static void onPlayerQuit(PlayerEvent.PlayerLoggedOutEvent event) {
        // Player data is serialized by the player mixin/capability provider.
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        CommonPlayerEvent.startTick(event.getServer());
        NekoEnergyBurstItem.tickComboBossBars(event.getServer());
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !(event.level instanceof ServerLevel level)) return;
        WeatherState next = new WeatherState(level.isRaining(), level.isThundering());
        WeatherState previous = WEATHER.put(level.dimension(), next);
        if (previous != null && !previous.equals(next)) {
            CommonWorldEvent.onWeatherChange(
                    level,
                    0,
                    0,
                    next.raining(),
                    next.thundering()
            );
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        WEATHER.remove(level.dimension());
        CommonWorldEvent.onWorldUnLoad(level.getServer(), level);
    }

    @SubscribeEvent
    public static void onUseBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        InteractionResult result = CommonPlayerInteractionEvent.useBlock(
                event.getEntity(), event.getLevel(), event.getHand(), event.getHitVec());
        applyInteractionResult(event, result);
    }

    @SubscribeEvent
    public static void onUseEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        if (event.getLevel().isClientSide()) return;
        InteractionResult result = CommonPlayerInteractionEvent.useEntity(
                event.getEntity(), event.getLevel(), event.getHand(), event.getTarget(),
                new EntityHitResult(event.getTarget(), event.getLocalPos()));
        applyInteractionResult(event, result);
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Entity target = event.getTarget();
        InteractionResult result = CommonPlayerInteractionEvent.onAttackEntity(
                event.getEntity(), event.getEntity().level(), net.minecraft.world.InteractionHand.MAIN_HAND,
                target, new EntityHitResult(target));
        if (result.consumesAction()) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        CommonPlayerInteractionEvent.onDamage(event.getEntity(), event.getSource(), event.getAmount());
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof INeko || !(event.getEntity() instanceof Monster)) return;
        Entity killer = event.getSource().getEntity();
        if (killer instanceof INeko neko && neko.isNeko()) {
            NekoLevelRegistry.combat().addRaw(neko, Math.max(1, event.getEntity().getMaxHealth() / 2.0));
        }
    }

    @SubscribeEvent
    public static void onStartSleep(PlayerSleepInBedEvent event) {
        if (event.getPos() != null) CommonPlayerEvent.startSleep(event.getEntity(), event.getPos());
    }

    @SubscribeEvent
    public static void onWakeUp(PlayerWakeUpEvent event) {
        event.getEntity().getSleepingPos().ifPresent(pos -> CommonPlayerEvent.stopSleep(event.getEntity(), pos));
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() != VillagerProfession.FARMER) return;
        event.getTrades().get(1).add((trader, random) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 2),
                ToNekoItems.CATNIP_SEED.getDefaultInstance(),
                10,
                10,
                1.1f
        ));
    }

    @SubscribeEvent
    public static void onServerChat(ServerChatEvent event) {
        if (!ConfigUtil.isChatEnable()) return;
        event.setCanceled(true);
        CommonChatEvent.onChatMessage(event.getRawText(), event.getPlayer());
    }

    private static void applyInteractionResult(PlayerInteractEvent event, InteractionResult result) {
        if (result == InteractionResult.PASS) return;
        event.setCancellationResult(result);
        event.setCanceled(true);
    }
}
