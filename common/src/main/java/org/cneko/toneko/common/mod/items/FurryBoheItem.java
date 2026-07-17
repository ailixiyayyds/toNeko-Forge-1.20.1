package org.cneko.toneko.common.mod.items;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.Level;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class FurryBoheItem extends Item {
    public static final String ID = "furry_bohe";

    public FurryBoheItem() {
        super(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag type) {
        tooltip.add(Component.translatable("item.toneko.furry_bohe.info"));
    }
}
