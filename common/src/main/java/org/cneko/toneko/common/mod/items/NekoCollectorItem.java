package org.cneko.toneko.common.mod.items;

import net.minecraft.world.level.Level;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.AABB;
import org.cneko.toneko.common.mod.codecs.CountCodecs;
import org.cneko.toneko.common.mod.entities.INeko;
import org.cneko.toneko.common.mod.misc.ToNekoAttributes;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NekoCollectorItem extends Item {
    public static String ID = "neko_collector";
    public static CountCodecs.FloatCountCodec DEFAULT_NEKO_PROGRESS_COMPONENT = new CountCodecs.FloatCountCodec(0.0f, 5000.0f);
    private static final String PROGRESS_KEY = "ToNekoProgress";
    private static final String MAX_PROGRESS_KEY = "ToNekoMaxProgress";
    public NekoCollectorItem() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag type) {
        float count = getProgress(stack);
        float maxCount = getMaxProgress(stack);
        tooltip.add(Component.translatable("item.toneko.neko_collector.info", count, maxCount).withStyle(ChatFormatting.GREEN));
    }

    private static float getProgress(ItemStack stack) {
        return stack.getOrCreateTag().getFloat(PROGRESS_KEY);
    }

    private static float getMaxProgress(ItemStack stack) {
        float value = stack.getOrCreateTag().getFloat(MAX_PROGRESS_KEY);
        return value > 0 ? value : DEFAULT_NEKO_PROGRESS_COMPONENT.getMaxCount();
    }

    private static void setProgress(ItemStack stack, float count, float maxCount) {
        stack.getOrCreateTag().putFloat(PROGRESS_KEY, count);
        stack.getOrCreateTag().putFloat(MAX_PROGRESS_KEY, maxCount);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof Player player) ||(world.isClientSide()) || player.isNeko()) return;
        // 获取玩家3格方块内的猫猫数量
        float radius = 3.0f;
        int catCount = 0;
        // 创建一个包围盒，它代表了以 centerEntity 为中心、半径为 radius 的区域
        AABB box = new AABB(entity.getX() - radius, entity.getY() - radius, entity.getZ() - radius,
                entity.getX() + radius, entity.getY() + radius, entity.getZ() + radius);
        List<Entity> entities = world.getEntities(entity, box);
        for (Entity entity1 : entities) {
            if (entity1 instanceof INeko) {
                catCount+= ((INeko) entity1).getNekoAbility();
            }
        }
        if (catCount==0) return;
        // 获取玩家的 属性附加值/100 + 1
        double neko_degree_addition = player.getAttributes().getValue(ToNekoAttributes.NEKO_DEGREE) / 100.0 + 1;
        // 原来的 count + 猫猫数量/100*neko_degree_addition
        float count = (float) (getProgress(stack) + catCount / 100.0f * neko_degree_addition);
        // 原来的maxCount
        float maxCount = getMaxProgress(stack);
        // 如果count >= maxCount，则清零并掉落一瓶猫娘药水
        if (count >= maxCount) {
            setProgress(stack, 0.0f, maxCount);
            entity.spawnAtLocation(ToNekoItems.NEKO_POTION);
        }else {
            setProgress(stack, count, maxCount);
        }


    }
}
