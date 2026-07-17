package org.cneko.toneko.common.mod.effects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;

import static org.cneko.toneko.common.Bootstrap.MODID;

public class HissIntimidationEffect extends MobEffect {
    public static final String ID = "hiss_intimidation";
    public static final ResourceLocation LOCATION = new ResourceLocation(MODID, ID);
    private static final String MODIFIER_UUID = "0bebc7b5-80ad-42d0-a8dc-e42799ba1df6";

    public HissIntimidationEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFFFFF);
        // 哈气威慑：减速 + 降低攻击
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_UUID, -0.15, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_UUID, -0.15, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        // 60%概率在受影响实体周围冒出白色气雾粒子，模拟"被哈气包围"
        if (entity.level().getRandom().nextFloat() < 0.6f) {
            entity.level().addParticle(
                ParticleTypes.CLOUD,
                entity.getX() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth() * 2,
                entity.getY() + entity.getBbHeight() / 2 + (entity.getRandom().nextDouble() - 0.5) * entity.getBbHeight(),
                entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth() * 2,
                0, 0.02, 0
            );
        }
        super.applyEffectTick(entity, amplifier);
    }
}
