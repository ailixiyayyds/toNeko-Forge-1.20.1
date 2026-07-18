package org.cneko.toneko.forge;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import org.cneko.toneko.common.mod.entities.INeko;
import org.cneko.toneko.common.mod.entities.NekoAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Forge-owned access point for the Neko state mixed into players.
 *
 * <p>The public common API remains {@link INeko}/{@link NekoAccess}, while
 * Forge integrations such as JustARod can query this capability without
 * depending on a concrete Mixin class.</p>
 */
public final class ToNekoForgePlayerCapability {
    public static final ResourceLocation ID = new ResourceLocation(ToNekoForge.MOD_ID, "neko_player_state");
    public static final Capability<INeko> NEKO_STATE =
            CapabilityManager.get(new CapabilityToken<>() {});

    private ToNekoForgePlayerCapability() {
    }

    public static void attach(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof Player player)) return;

        INeko state = NekoAccess.find(player);
        if (state == null) return;

        Provider provider = new Provider(state);
        event.addCapability(ID, provider);
        event.addListener(provider::invalidate);
    }

    public static void copyOnClone(PlayerEvent.Clone event) {
        INeko original = NekoAccess.find(event.getOriginal());
        INeko replacement = NekoAccess.find(event.getEntity());
        if (original != null && replacement != null) {
            replacement.copyNekoStateFrom(original);
        }
    }

    public static LazyOptional<INeko> get(Player player) {
        return player.getCapability(NEKO_STATE);
    }

    private static final class Provider implements ICapabilityProvider {
        private final LazyOptional<INeko> state;

        private Provider(INeko state) {
            this.state = LazyOptional.of(() -> state);
        }

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> capability,
                                                         @Nullable Direction side) {
            return capability == NEKO_STATE ? state.cast() : LazyOptional.empty();
        }

        private void invalidate() {
            state.invalidate();
        }
    }
}
