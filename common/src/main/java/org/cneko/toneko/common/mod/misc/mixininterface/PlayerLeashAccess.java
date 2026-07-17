package org.cneko.toneko.common.mod.misc.mixininterface;

import net.minecraft.world.entity.Entity;

public interface PlayerLeashAccess {
    boolean toneko$isLeashed();
    void toneko$setLeashedTo(Entity holder, boolean broadcastPacket);
    void toneko$dropLeash(boolean broadcastPacket, boolean dropLead);
}
