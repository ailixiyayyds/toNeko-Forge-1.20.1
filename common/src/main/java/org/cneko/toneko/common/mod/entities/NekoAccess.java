package org.cneko.toneko.common.mod.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/**
 * Loader-neutral access point for Neko state attached to vanilla entities.
 *
 * The Fabric build exposes this state through Loom-injected interfaces. Forge
 * code must use this adapter instead of assuming that {@link Player} declares
 * the injected methods at Java compile time.
 */
public final class NekoAccess {
    private NekoAccess() {
    }

    public static boolean isNeko(@Nullable Entity entity) {
        return entity instanceof INeko neko && neko.isNeko();
    }

    public static @Nullable INeko find(@Nullable Entity entity) {
        return entity instanceof INeko neko ? neko : null;
    }

    public static float getEnergy(@Nullable Entity entity) {
        INeko neko = find(entity);
        return neko == null ? 0.0F : neko.getNekoEnergy();
    }

    public static void setEnergy(Entity entity, float energy) {
        require(entity).setNekoEnergy(energy);
    }

    public static float getLevel(@Nullable Entity entity) {
        INeko neko = find(entity);
        return neko == null ? 0.0F : neko.getNekoLevel();
    }

    public static float getMaxEnergy(@Nullable Entity entity) {
        INeko neko = find(entity);
        return neko == null ? 0.0F : neko.getMaxNekoEnergy();
    }

    public static INeko require(Entity entity) {
        INeko neko = find(entity);
        if (neko == null) {
            throw new IllegalStateException("Neko state is unavailable for " + entity.getType());
        }
        return neko;
    }
}
