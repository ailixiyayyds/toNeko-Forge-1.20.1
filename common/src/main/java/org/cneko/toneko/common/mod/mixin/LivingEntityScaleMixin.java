package org.cneko.toneko.common.mod.mixin;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import org.cneko.toneko.common.mod.entities.NekoEntity;
import org.cneko.toneko.common.mod.misc.ToNekoAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityScaleMixin {
    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void toneko$scaleNekoDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof NekoEntity)) return;

        double scale = entity.getAttributeValue(ToNekoAttributes.SCALE);
        if (Double.isFinite(scale) && Math.abs(scale - 1.0) > 0.0001) {
            cir.setReturnValue(cir.getReturnValue().scale((float) scale));
        }
    }
}
