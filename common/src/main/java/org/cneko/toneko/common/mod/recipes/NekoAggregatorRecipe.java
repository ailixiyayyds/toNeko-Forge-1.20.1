package org.cneko.toneko.common.mod.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NekoAggregatorRecipe implements Recipe<NekoAggregatorInput> {
    private final ResourceLocation id;
    public final NekoAggregatorRecipePattern pattern;
    final ItemStack result;
    public final double energy;

    public NekoAggregatorRecipe(ResourceLocation id, NekoAggregatorRecipePattern pattern, double energy, ItemStack result) {
        this.id = id;
        this.pattern = pattern;
        this.result = result;
        this.energy = energy;
    }

    public boolean matches(@NotNull NekoAggregatorInput input, @NotNull Level level) { return pattern.matches(input); }
    public @NotNull ItemStack assemble(@NotNull NekoAggregatorInput input, @NotNull RegistryAccess registries) { return result.copy(); }
    public boolean canCraftInDimensions(int width, int height) { return width >= pattern.width() && height >= pattern.height(); }
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registries) { return result.copy(); }
    public @NotNull RecipeSerializer<?> getSerializer() { return ToNekoRecipes.NEKO_AGGREGATOR_SERIALIZER; }
    public @NotNull RecipeType<?> getType() { return ToNekoRecipes.NEKO_AGGREGATOR; }
    public @NotNull ResourceLocation getId() { return id; }

    public static class Serializer implements RecipeSerializer<NekoAggregatorRecipe> {
        public NekoAggregatorRecipe fromJson(ResourceLocation id, JsonObject json) {
            NekoAggregatorRecipePattern pattern = NekoAggregatorRecipePattern.fromJson(json);
            double energy = GsonHelper.getAsDouble(json, "energy", 0);
            ItemStack result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            return new NekoAggregatorRecipe(id, pattern, energy, result);
        }

        public @Nullable NekoAggregatorRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            NekoAggregatorRecipePattern pattern = NekoAggregatorRecipePattern.fromNetwork(buffer);
            ItemStack result = buffer.readItem();
            double energy = buffer.readDouble();
            return new NekoAggregatorRecipe(id, pattern, energy, result);
        }

        public void toNetwork(FriendlyByteBuf buffer, NekoAggregatorRecipe recipe) {
            recipe.pattern.toNetwork(buffer);
            buffer.writeItem(recipe.result);
            buffer.writeDouble(recipe.energy);
        }
    }
}
