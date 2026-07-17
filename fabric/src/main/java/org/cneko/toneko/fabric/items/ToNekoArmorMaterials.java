package org.cneko.toneko.fabric.items;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public final class ToNekoArmorMaterials {
    public static final ArmorMaterial NEKO = new NekoArmorMaterial();

    private ToNekoArmorMaterials() {
    }

    public static void init() {
    }

    private static final class NekoArmorMaterial implements ArmorMaterial {
        public int getDurabilityForType(ArmorItem.@NotNull Type type) { return 20; }
        public int getDefenseForType(ArmorItem.@NotNull Type type) { return 2; }
        public int getEnchantmentValue() { return 15; }
        public @NotNull SoundEvent getEquipSound() { return SoundEvents.CAT_AMBIENT; }
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(TagKey.create(BuiltInRegistries.ITEM.key(), new ResourceLocation("c", "wool")));
        }
        public String getName() { return "toneko:neko_armor"; }
        public float getToughness() { return 0.5f; }
        public float getKnockbackResistance() { return 0; }
    }
}
