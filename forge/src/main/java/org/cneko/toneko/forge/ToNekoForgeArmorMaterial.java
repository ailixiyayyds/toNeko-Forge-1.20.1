package org.cneko.toneko.forge;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public enum ToNekoForgeArmorMaterial implements ArmorMaterial {
    INSTANCE;

    @Override
    public int getDurabilityForType(ArmorItem.@NotNull Type type) {
        return 20;
    }

    @Override
    public int getDefenseForType(ArmorItem.@NotNull Type type) {
        return 2;
    }

    @Override
    public int getEnchantmentValue() {
        return 15;
    }

    @Override
    public @NotNull SoundEvent getEquipSound() {
        return SoundEvents.CAT_AMBIENT;
    }

    @Override
    public @NotNull Ingredient getRepairIngredient() {
        return Ingredient.of(TagKey.create(BuiltInRegistries.ITEM.key(),
                new ResourceLocation("c", "wool")));
    }

    @Override
    public @NotNull String getName() {
        return "toneko:neko_armor";
    }

    @Override
    public float getToughness() {
        return 0.5f;
    }

    @Override
    public float getKnockbackResistance() {
        return 0.0f;
    }
}
