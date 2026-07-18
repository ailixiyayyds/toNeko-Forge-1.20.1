package org.cneko.toneko.common.mod.items;

import net.minecraft.world.level.Level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.cneko.toneko.common.mod.effects.ToNekoEffects;
import org.cneko.toneko.common.mod.entities.INeko;
import org.cneko.toneko.common.mod.packets.NekoInfoSyncPayload;
import org.cneko.toneko.common.mod.packets.ToNekoNetworking;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CatnipItem extends Item implements BazookaItem.Ammunition {
    public CatnipItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
        // Keep vanilla eating/consumption behavior on both logical sides. The
        // old implementation only called eat() on the server, leaving the
        // client use animation and inventory state out of step.
        ItemStack result = livingEntity.eat(level, stack);
        FoodProperties foodProperties = stack.getItem().getFoodProperties();
        if (foodProperties != null && !livingEntity.level().isClientSide) {
            if (livingEntity instanceof INeko neko && neko.isNeko()){
                livingEntity.addEffect(new MobEffectInstance(
                        ToNekoEffects.NEKO_EFFECT,
                        10000,
                        0
                ));
                // 恢复猫猫能量并限制在当前上限内。
                neko.setNekoEnergy(Math.min(neko.getMaxNekoEnergy(), neko.getNekoEnergy() + 30.0F));

                // The regular slow-tick sync can be almost one second late.
                // Send the updated value immediately so the HUD responds as
                // soon as eating completes.
                if (livingEntity instanceof ServerPlayer serverPlayer) {
                    ToNekoNetworking.send(serverPlayer, new NekoInfoSyncPayload(
                            neko.getNekoEnergy(),
                            neko.getMaxNekoEnergy(),
                            neko.getNekoLevelFactorRaw("interaction"),
                            neko.getNekoLevelFactorRaw("combat"),
                            neko.getNekoLevelFactorRaw("base"),
                            neko.isNeko(),
                            neko.getNekoAge()
                    ));
                }
            }
        }
        return result;
    }

    @Override
    public void hitOnEntity(LivingEntity shooter, LivingEntity target, ItemStack bazooka, ItemStack ammunition) {
        if (!shooter.level().isClientSide) {
            if (target instanceof INeko neko && neko.isNeko()) {
                target.addEffect(new MobEffectInstance(
                        ToNekoEffects.NEKO_EFFECT,
                        10000,
                        0
                ));
            }
        }
    }

    @Override
    public void hitOnBlock(LivingEntity shooter, BlockPos pos, ItemStack bazooka, ItemStack ammunition) {
        hitOnAir(shooter, pos, bazooka, ammunition);
    }

    @Override
    public void hitOnAir(LivingEntity shooter, BlockPos pos, ItemStack bazooka, ItemStack ammunition) {
        // 粒子
        if (!shooter.level().isClientSide) {
            shooter.level().addParticle(
                    ParticleTypes.EFFECT,
                    pos.getX() + 0.5,
                    pos.getY() + 0.5,
                    pos.getZ() + 0.5,
                    0,
                    0,
                    0
            );
        }
    }

    @Override
    public float getSpeed(ItemStack bazooka, ItemStack ammunition) {
        return 0.8f;
    }

    @Override
    public float getMaxDistance(ItemStack bazooka, ItemStack ammunition) {
        return 30;
    }

    @Override
    public int getCooldownTicks(ItemStack bazooka, ItemStack ammunition) {
        return 5;
    }

    public static class InfiniteCatnipItem extends CatnipItem {
        public InfiniteCatnipItem(Properties properties) {
            super(properties);
        }

        @Override
        public @NotNull ItemStack finishUsingItem(ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity) {
            // 1. 在消耗前先备份物品栈（因为父类逻辑会消耗物品）
            ItemStack returnStack = stack.copy();

            // 2. 如果使用者是玩家，添加 5 秒冷却时间
            if (livingEntity instanceof Player player) {
                player.getCooldowns().addCooldown(this, 100);
            }

            // 3. 执行父类逻辑
            super.finishUsingItem(stack, level, livingEntity);

            // 4. 实现“无限”逻辑
            if (livingEntity instanceof Player player) {
                return returnStack;
            }

            // 对于非玩家实体，返回原本处理后的 stack 即可
            return stack;
        }

        @Override
        public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
            super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
            tooltipComponents.add(Component.translatable("item.toneko.infinite_catnip.tip"));
        }
    }

}
