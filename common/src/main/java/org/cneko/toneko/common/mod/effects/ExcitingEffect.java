package org.cneko.toneko.common.mod.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.cneko.toneko.common.mod.misc.ToNekoAttributes;
import org.jetbrains.annotations.NotNull;

import static org.cneko.toneko.common.Bootstrap.MODID;

public class ExcitingEffect extends MobEffect {
    public static final String ID = "exciting";
    public static final ResourceLocation LOCATION = new ResourceLocation(MODID, ID);
    private static final String MODIFIER_UUID = "da9da7f3-da76-48f6-b346-9b863b4fce09";

    public ExcitingEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFF00FF);
        this.addAttributeModifier(ToNekoAttributes.NEKO_DEGREE, MODIFIER_UUID, 0.05, AttributeModifier.Operation.ADDITION);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, MODIFIER_UUID, 0.3, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, MODIFIER_UUID, 0.3, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.JUMP_STRENGTH, MODIFIER_UUID, 0.3, AttributeModifier.Operation.MULTIPLY_BASE);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, MODIFIER_UUID, 0.3, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity entity, int amplifier) {
        super.applyEffectTick(entity, amplifier);
    }
}
