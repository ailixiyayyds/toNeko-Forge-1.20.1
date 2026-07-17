package org.cneko.toneko.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.cneko.toneko.common.mod.items.ToNekoItems;

import java.util.function.BiConsumer;

import static org.cneko.toneko.common.Bootstrap.MODID;

public class ChestLootTablesProvider extends SimpleFabricLootTableProvider {
    public static final ResourceLocation NEKO_CHEST = new ResourceLocation(MODID, "chests/neko_loot");

    public ChestLootTablesProvider(FabricDataOutput output) {
        super(output, LootContextParamSets.CHEST);
    }

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        consumer.accept(NEKO_CHEST, LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(0.8F))
                        .add(LootItem.lootTableItem(ToNekoItems.NEKO_POTION)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(3F))))
                        .add(LootItem.lootTableItem(ToNekoItems.NEKO_EARS)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1F))))
                        .add(LootItem.lootTableItem(ToNekoItems.NEKO_TAIL)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2F))))));
    }
}
