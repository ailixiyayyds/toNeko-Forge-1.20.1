package org.cneko.toneko.common.mod.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NekoAggregatorRecipePattern {
    private final int width;
    private final int height;
    private final NonNullList<Ingredient> ingredients;
    private final int ingredientCount;

    public NekoAggregatorRecipePattern(int width, int height, NonNullList<Ingredient> ingredients) {
        this.width = width;
        this.height = height;
        this.ingredients = ingredients;
        int count = 0;
        for (Ingredient ingredient : ingredients) if (!ingredient.isEmpty()) count++;
        this.ingredientCount = count;
    }

    public static NekoAggregatorRecipePattern fromJson(JsonObject json) {
        Map<Character, Ingredient> keys = new HashMap<>();
        JsonObject keyJson = GsonHelper.getAsJsonObject(json, "key");
        for (Map.Entry<String, JsonElement> entry : keyJson.entrySet()) {
            if (entry.getKey().length() != 1 || " ".equals(entry.getKey())) {
                throw new IllegalArgumentException("Invalid recipe key: " + entry.getKey());
            }
            keys.put(entry.getKey().charAt(0), Ingredient.fromJson(entry.getValue()));
        }

        JsonArray patternJson = GsonHelper.getAsJsonArray(json, "pattern");
        List<String> rows = new ArrayList<>();
        for (JsonElement row : patternJson) rows.add(row.getAsString());
        String[] pattern = shrink(rows);
        if (pattern.length == 0) throw new IllegalArgumentException("Empty recipe pattern");
        int width = pattern[0].length();
        if (width > 3 || pattern.length > 3) throw new IllegalArgumentException("Recipe pattern is larger than 3x3");

        NonNullList<Ingredient> ingredients = NonNullList.withSize(width * pattern.length, Ingredient.EMPTY);
        for (int y = 0; y < pattern.length; y++) {
            if (pattern[y].length() != width) throw new IllegalArgumentException("Recipe rows must have equal width");
            for (int x = 0; x < width; x++) {
                char symbol = pattern[y].charAt(x);
                Ingredient ingredient = symbol == ' ' ? Ingredient.EMPTY : keys.get(symbol);
                if (ingredient == null) throw new IllegalArgumentException("Undefined recipe symbol: " + symbol);
                ingredients.set(x + y * width, ingredient);
            }
        }
        return new NekoAggregatorRecipePattern(width, pattern.length, ingredients);
    }

    private static String[] shrink(List<String> rows) {
        int minColumn = Integer.MAX_VALUE;
        int maxColumn = -1;
        int firstRow = 0;
        int lastRow = rows.size() - 1;
        while (firstRow <= lastRow && rows.get(firstRow).trim().isEmpty()) firstRow++;
        while (lastRow >= firstRow && rows.get(lastRow).trim().isEmpty()) lastRow--;
        for (int row = firstRow; row <= lastRow; row++) {
            String value = rows.get(row);
            int first = 0;
            while (first < value.length() && value.charAt(first) == ' ') first++;
            int last = value.length() - 1;
            while (last >= 0 && value.charAt(last) == ' ') last--;
            if (last >= first) {
                minColumn = Math.min(minColumn, first);
                maxColumn = Math.max(maxColumn, last);
            }
        }
        if (maxColumn < minColumn) return new String[0];
        String[] result = new String[lastRow - firstRow + 1];
        for (int i = 0; i < result.length; i++) result[i] = rows.get(firstRow + i).substring(minColumn, maxColumn + 1);
        return result;
    }

    public boolean matches(NekoAggregatorInput input) {
        if (input.ingredientCount() != ingredientCount || input.width() != width || input.height() != height) return false;
        return matches(input, false) || matches(input, true);
    }

    private boolean matches(NekoAggregatorInput input, boolean mirrored) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int patternX = mirrored ? width - x - 1 : x;
                if (!ingredients.get(patternX + y * width).test(input.getItem(x, y))) return false;
            }
        }
        return true;
    }

    public void toNetwork(FriendlyByteBuf buffer) {
        buffer.writeVarInt(width).writeVarInt(height);
        for (Ingredient ingredient : ingredients) ingredient.toNetwork(buffer);
    }

    public static NekoAggregatorRecipePattern fromNetwork(FriendlyByteBuf buffer) {
        int width = buffer.readVarInt();
        int height = buffer.readVarInt();
        NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
        for (int i = 0; i < ingredients.size(); i++) ingredients.set(i, Ingredient.fromNetwork(buffer));
        return new NekoAggregatorRecipePattern(width, height, ingredients);
    }

    public int width() { return width; }
    public int height() { return height; }
    public NonNullList<Ingredient> ingredients() { return ingredients; }
}
