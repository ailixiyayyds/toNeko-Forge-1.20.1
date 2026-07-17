package org.cneko.toneko.common.mod.util;

import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class EnchantmentUtil {
    public static boolean hasEnchantment(ResourceLocation id, ItemStack stack){
        AtomicBoolean returnValue = new AtomicBoolean(false);
        EnchantmentHelper.getEnchantments(stack).keySet().forEach(enchantment -> {
            if(BuiltInRegistries.ENCHANTMENT.getKey(enchantment).equals(id)){
                returnValue.set(true);
            }
        });
        return returnValue.get();
    }

    public static int getEnchantmentLevel(Enchantment enchantment, ItemStack stack, Level level){
        return EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
    }

    public static int getEnchantmentLevel(ResourceKey<Enchantment> enchantment, ItemStack stack, Level level){
        for (var entry : EnchantmentHelper.getEnchantments(stack).entrySet()) {
            if (BuiltInRegistries.ENCHANTMENT.getKey(entry.getKey()).equals(enchantment.location())) {
                return entry.getValue();
            }
        }
        return 0;
    }
}
