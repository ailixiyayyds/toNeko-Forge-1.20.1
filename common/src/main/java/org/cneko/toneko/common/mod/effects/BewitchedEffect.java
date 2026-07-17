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

public class BewitchedEffect extends MobEffect {
    public static final String ID = "bewitched";
    public static final ResourceLocation LOCATION = new ResourceLocation(MODID, ID);
    private static final String MODIFIER_UUID = "1fb677ba-7161-4f34-aee0-b911384b3a11";

    public BewitchedEffect() {
        super(MobEffectCategory.HARMFUL, 0xFFB6C1);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_UUID, -0.03, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, MODIFIER_UUID, -0.1, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.JUMP_STRENGTH, MODIFIER_UUID, -0.1, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_UUID, -0.5, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        // 10%的概率冒出一颗爱心
        if (entity.level().getRandom().nextFloat() < 0.1f) {
            entity.level().addParticle(
                ParticleTypes.HEART,
                entity.getX() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(),
                entity.getY() + entity.getBbHeight() / 2,
                entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth(),
                0, 0, 0
            );
        }
        super.applyEffectTick(entity, amplifier);
    }
}
