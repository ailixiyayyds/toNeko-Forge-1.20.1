package org.cneko.toneko.forge;

import net.minecraft.core.registries.Registries;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.cneko.toneko.common.mod.api.NekoSkinRegistry;
import org.cneko.toneko.common.mod.advencements.GiftNekoTrigger;
import org.cneko.toneko.common.mod.advencements.NekoLevelTrigger;
import org.cneko.toneko.common.mod.blocks.CatnipBlock;
import org.cneko.toneko.common.mod.blocks.NekoAggregatorBlock;
import org.cneko.toneko.common.mod.effects.BewitchedEffect;
import org.cneko.toneko.common.mod.effects.ExcitingEffect;
import org.cneko.toneko.common.mod.effects.HissIntimidationEffect;
import org.cneko.toneko.common.mod.entities.AdventurerNeko;
import org.cneko.toneko.common.mod.entities.AmmunitionEntity;
import org.cneko.toneko.common.mod.entities.CrystalNekoEntity;
import org.cneko.toneko.common.mod.entities.FightingNekoEntity;
import org.cneko.toneko.common.mod.entities.GhostNekoEntity;
import org.cneko.toneko.common.mod.entities.NoelleMaidNekoEntity;
import org.cneko.toneko.common.mod.entities.RavennEntity;
import org.cneko.toneko.common.mod.entities.boss.mouflet.MoufletNekoBoss;
import org.cneko.toneko.common.mod.genetics.api.GeneticsDataLoader;
import org.cneko.toneko.common.mod.items.BazookaItem;
import org.cneko.toneko.common.mod.items.CatnipItem;
import org.cneko.toneko.common.mod.items.ContractItem;
import org.cneko.toneko.common.mod.items.DeageTreatItem;
import org.cneko.toneko.common.mod.items.EvilNekoEnergyBurstItem;
import org.cneko.toneko.common.mod.items.FurryBoheItem;
import org.cneko.toneko.common.mod.items.GeneEditorItem;
import org.cneko.toneko.common.mod.items.GrowthTreatItem;
import org.cneko.toneko.common.mod.items.NekoArmor;
import org.cneko.toneko.common.mod.items.NekoCollectorItem;
import org.cneko.toneko.common.mod.items.NekoEnergyBurstItem;
import org.cneko.toneko.common.mod.items.NekoEnergyStorageItem;
import org.cneko.toneko.common.mod.items.NekoPotionItem;
import org.cneko.toneko.common.mod.items.PlotScrollItem;
import org.cneko.toneko.common.mod.items.ammo.ExplosiveBombItem;
import org.cneko.toneko.common.mod.items.ammo.LightningBombItem;
import org.cneko.toneko.common.mod.items.ammo.NekoEnergyBombItem;
import org.cneko.toneko.common.mod.misc.ToNekoSongs;
import org.cneko.toneko.common.mod.misc.ToNekoSoundEvents;
import org.cneko.toneko.common.mod.recipes.NekoAggregatorRecipe;
import org.cneko.toneko.common.util.ConfigUtil;

import java.util.function.Predicate;

public final class ToNekoForgeContent {
    private static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ToNekoForge.MOD_ID);
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ToNekoForge.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ToNekoForge.MOD_ID);
    private static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ToNekoForge.MOD_ID);
    private static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ToNekoForge.MOD_ID);
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ToNekoForge.MOD_ID);
    private static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ToNekoForge.MOD_ID);
    private static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ToNekoForge.MOD_ID);
    private static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ToNekoForge.MOD_ID);
    private static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ToNekoForge.MOD_ID);
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ToNekoForge.MOD_ID);

    private static final net.minecraftforge.registries.RegistryObject<Item> ADVENTURER_NEKO_SPAWN_EGG =
            ITEMS.register("adventurer_neko_spawn_egg", () -> new ForgeSpawnEggItem(
                    () -> org.cneko.toneko.common.mod.entities.ToNekoEntities.ADVENTURER_NEKO,
                    0x7e7e7e, 0xffffff, new Item.Properties()));
    private static final net.minecraftforge.registries.RegistryObject<Item> GHOST_NEKO_SPAWN_EGG =
            ITEMS.register("ghost_neko_spawn_egg", () -> new ForgeSpawnEggItem(
                    () -> org.cneko.toneko.common.mod.entities.ToNekoEntities.GHOST_NEKO,
                    0x7e7e7e, 0xffffff, new Item.Properties()));
    private static final net.minecraftforge.registries.RegistryObject<Item> FIGHTING_NEKO_SPAWN_EGG =
            ITEMS.register("fighting_neko_spawn_egg", () -> new ForgeSpawnEggItem(
                    () -> org.cneko.toneko.common.mod.entities.ToNekoEntities.FIGHTING_NEKO,
                    0x7e7e7e, 0xffffff, new Item.Properties()));
    private static final net.minecraftforge.registries.RegistryObject<Item> NOELLE_MAID_NEKO_SPAWN_EGG =
            ITEMS.register("noelle_maid_neko_spawn_egg", () -> new ForgeSpawnEggItem(
                    () -> org.cneko.toneko.common.mod.entities.ToNekoEntities.NOELLE_MAID_NEKO,
                    0xffc0cb, 0xffffff, new Item.Properties()));

    static {
        ENCHANTMENTS.register("reversion", () -> new ItemEnchantment(Enchantment.Rarity.RARE, 1, 1, 1,
                stack -> stack.is(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_EARS)
                        || stack.is(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_TAIL)
                        || stack.is(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_PAWS),
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET));
        ENCHANTMENTS.register("enforcement", () -> new ItemEnchantment(Enchantment.Rarity.COMMON, 1, 1, 1,
                stack -> stack.is(org.cneko.toneko.common.mod.items.ToNekoItems.CONTRACT),
                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND));
        ENCHANTMENTS.register("hiss_power", () -> energyBurstEnchantment(Enchantment.Rarity.COMMON, 3, 1, 10));
        ENCHANTMENTS.register("hiss_spread", () -> energyBurstEnchantment(Enchantment.Rarity.UNCOMMON, 3, 1, 10));
        ENCHANTMENTS.register("hiss_efficiency", () -> energyBurstEnchantment(Enchantment.Rarity.UNCOMMON, 3, 1, 10));
        ENCHANTMENTS.register("combo_extend", () -> energyBurstEnchantment(Enchantment.Rarity.RARE, 2, 5, 15));
        ENCHANTMENTS.register("hiss_root", () -> energyBurstEnchantment(Enchantment.Rarity.RARE, 2, 1, 12));
        ENCHANTMENTS.register("hiss_demolish", () -> energyBurstEnchantment(Enchantment.Rarity.RARE, 3, 1, 12));

        ATTRIBUTES.register("neko.degree", () ->
                org.cneko.toneko.common.mod.misc.ToNekoAttributes.NEKO_DEGREE);
        ATTRIBUTES.register("neko.max_energy", () ->
                org.cneko.toneko.common.mod.misc.ToNekoAttributes.MAX_NEKO_ENERGY);
        ATTRIBUTES.register("neko.scale", () ->
                org.cneko.toneko.common.mod.misc.ToNekoAttributes.SCALE);

        ENTITY_TYPES.register("adventurer_neko", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.ADVENTURER_NEKO =
                        EntityType.Builder.of(AdventurerNeko::new, MobCategory.CREATURE)
                                .sized(0.5f, 1.7f).clientTrackingRange(8)
                                .build("adventurer_neko"));
        ENTITY_TYPES.register("crystal_neko", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.CRYSTAL_NEKO =
                        EntityType.Builder.of(CrystalNekoEntity::new, MobCategory.CREATURE)
                                .sized(0.5f, 1.7f).clientTrackingRange(8)
                                .build("crystal_neko"));
        ENTITY_TYPES.register("ghost_neko", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.GHOST_NEKO =
                        EntityType.Builder.of(GhostNekoEntity::new, MobCategory.CREATURE)
                                .sized(0.4f, 1.2f).clientTrackingRange(8)
                                .build("ghost_neko"));
        ENTITY_TYPES.register("fighting_neko", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.FIGHTING_NEKO =
                        EntityType.Builder.of(FightingNekoEntity::new, MobCategory.CREATURE)
                                .sized(0.5f, 1.7f).clientTrackingRange(8)
                                .build("fighting_neko"));
        ENTITY_TYPES.register("mouflet_neko_boss", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.MOUFLET_NEKO_BOSS =
                        EntityType.Builder.of(MoufletNekoBoss::new, MobCategory.MONSTER)
                                .sized(0.5f, 1.7f).clientTrackingRange(8).updateInterval(3)
                                .build("mouflet_neko_boss"));
        ENTITY_TYPES.register("ravenn", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.RAVENN_ENTITY =
                        EntityType.Builder.of(RavennEntity::new, MobCategory.CREATURE)
                                .sized(0.5f, 1.7f).clientTrackingRange(8)
                                .build("ravenn"));
        ENTITY_TYPES.register("noelle_maid_neko", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.NOELLE_MAID_NEKO =
                        EntityType.Builder.of(NoelleMaidNekoEntity::new, MobCategory.CREATURE)
                                .sized(0.5f, 1.7f).clientTrackingRange(8)
                                .build("noelle_maid_neko"));
        ENTITY_TYPES.register("ammunition_entity", () ->
                org.cneko.toneko.common.mod.entities.ToNekoEntities.AMMUNITION_ENTITY =
                        EntityType.Builder.of(AmmunitionEntity::new, MobCategory.MISC)
                                .sized(0.25f, 0.25f).clientTrackingRange(4).updateInterval(20)
                                .build("ammunition_entity"));

        BLOCKS.register("catnip", () ->
                org.cneko.toneko.common.mod.blocks.ToNekoBlocks.CATNIP = new CatnipBlock());
        BLOCKS.register("neko_aggregator", () ->
                org.cneko.toneko.common.mod.blocks.ToNekoBlocks.NEKO_AGGREGATOR =
                        new NekoAggregatorBlock(BlockBehaviour.Properties.of().strength(5.0f)
                                .requiresCorrectToolForDrops()));
        BLOCKS.register("neko_block", () ->
                org.cneko.toneko.common.mod.blocks.ToNekoBlocks.NEKO_BLOCK =
                        new Block(BlockBehaviour.Properties.of().strength(5.0f)
                                .requiresCorrectToolForDrops()));
        BLOCKS.register("neko_diamond_block", () ->
                org.cneko.toneko.common.mod.blocks.ToNekoBlocks.NEKO_DIAMOND_BLOCK =
                        new Block(BlockBehaviour.Properties.of().strength(5.0f)
                                .requiresCorrectToolForDrops()));

        SOUND_EVENTS.register("music.kawaii", () -> ToNekoSongs.KAWAII);
        SOUND_EVENTS.register("music.never_gonna_give_you_up", () ->
                ToNekoSongs.NEVER_GONNA_GIVE_YOU_UP);
        SOUND_EVENTS.register("item.bazooka.biu", () -> ToNekoSoundEvents.BAZOOKA_BIU);
        SOUND_EVENTS.register("item.bazooka.meow", () -> ToNekoSoundEvents.BAZOOKA_MEOW);
        SOUND_EVENTS.register("entity.neko.alarm", () -> ToNekoSoundEvents.NEKO_ALARM);

        ITEMS.register(NekoPotionItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_POTION = new NekoPotionItem());
        ITEMS.register(NekoCollectorItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_COLLECTOR = new NekoCollectorItem());
        ITEMS.register(FurryBoheItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.FURRY_BOHE = new FurryBoheItem());
        ITEMS.register(NekoArmor.NekoEarsItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_EARS =
                        new NekoArmor.NekoEarsItem(ToNekoForgeArmorMaterial.INSTANCE));
        ITEMS.register(NekoArmor.NekoTailItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_TAIL =
                        new NekoArmor.NekoTailItem(ToNekoForgeArmorMaterial.INSTANCE));
        ITEMS.register(NekoArmor.NekoPawsItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_PAWS =
                        new NekoArmor.NekoPawsItem(ToNekoForgeArmorMaterial.INSTANCE));
        ITEMS.register("catnip", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.CATNIP = new CatnipItem(
                        new Item.Properties().food(new FoodProperties.Builder()
                                .nutrition(2).saturationMod(1.0f).alwaysEat().build())));
        ITEMS.register("infinite_catnip", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.INFINITE_CATNIP =
                        new CatnipItem.InfiniteCatnipItem(new Item.Properties()
                                .food(new FoodProperties.Builder().nutrition(2)
                                        .saturationMod(1.0f).build())
                                .rarity(Rarity.UNCOMMON)));
        ITEMS.register("catnip_sandwich", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.CATNIP_SANDWICH = new CatnipItem(
                        new Item.Properties().food(new FoodProperties.Builder()
                                .nutrition(10).saturationMod(12.0f).build())));
        ITEMS.register("catnip_seed", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.CATNIP_SEED =
                        new ItemNameBlockItem(org.cneko.toneko.common.mod.blocks.ToNekoBlocks.CATNIP,
                                new Item.Properties()));
        ITEMS.register("music_disc_kawaii", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.MUSIC_DISC_KAWAII =
                        new RecordItem(10, ToNekoSongs.KAWAII,
                                new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 180));
        ITEMS.register("music_disc_never_gonna_give_you_up", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.MUSIC_DISC_NEVER_GONNA_GIVE_YOU_UP =
                        new RecordItem(10, ToNekoSongs.NEVER_GONNA_GIVE_YOU_UP,
                                new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 213));
        ITEMS.register(BazookaItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.BAZOOKA =
                        new BazookaItem(new Item.Properties()));
        ITEMS.register("plot_scroll", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.PLOT_SCROLL =
                        new PlotScrollItem(new Item.Properties()));
        ITEMS.register("lightning_bomb", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.LIGHTNING_BOMB =
                        new LightningBombItem(new Item.Properties()));
        ITEMS.register("explosive_bomb", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.EXPLOSIVE_BOMB =
                        new ExplosiveBombItem(new Item.Properties()));
        ITEMS.register("energy_bomb", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.ENERGY_BOMB =
                        new NekoEnergyBombItem());
        ITEMS.register("contract", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.CONTRACT =
                        new ContractItem(new Item.Properties()));
        ITEMS.register("neko_aggregator", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_AGGREGATOR_ITEM =
                        new ItemNameBlockItem(
                                org.cneko.toneko.common.mod.blocks.ToNekoBlocks.NEKO_AGGREGATOR,
                                new Item.Properties()));
        ITEMS.register("neko_ingot", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_INGOT = new Item(new Item.Properties()));
        ITEMS.register("neko_block", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_BLOCK = new BlockItem(
                        org.cneko.toneko.common.mod.blocks.ToNekoBlocks.NEKO_BLOCK, new Item.Properties()));
        ITEMS.register("neko_diamond", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_DIAMOND = new Item(new Item.Properties()));
        ITEMS.register("neko_diamond_block", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_DIAMOND_BLOCK = new BlockItem(
                        org.cneko.toneko.common.mod.blocks.ToNekoBlocks.NEKO_DIAMOND_BLOCK,
                        new Item.Properties()));
        ITEMS.register("neko_crystal", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_CRYSTAL = new Item(new Item.Properties()));
        ITEMS.register("neko_energy_storage_small", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_SMALL =
                        new NekoEnergyStorageItem(150, false));
        ITEMS.register("neko_energy_storage_small_charged", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_SMALL_CHARGED =
                        new NekoEnergyStorageItem(150, true));
        ITEMS.register("neko_energy_storage_medium", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_MEDIUM =
                        new NekoEnergyStorageItem(400, false));
        ITEMS.register("neko_energy_storage_medium_charged", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_MEDIUM_CHARGED =
                        new NekoEnergyStorageItem(400, true));
        ITEMS.register("neko_energy_storage_large", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_LARGE =
                        new NekoEnergyStorageItem(1000, false));
        ITEMS.register("neko_energy_storage_large_charged", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_LARGE_CHARGED =
                        new NekoEnergyStorageItem(1000, true));
        ITEMS.register("neko_energy_burst", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_BURST =
                        new NekoEnergyBurstItem(2.0f, 3.0f, 50.0f));
        ITEMS.register("evil_neko_energy_burst", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.EVIL_NEKO_ENERGY_BURST =
                        new EvilNekoEnergyBurstItem(2.0f, 3.0f, 50.0f));
        ITEMS.register("gene_editor", () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.GENE_EDITOR =
                        new GeneEditorItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));
        ITEMS.register(GrowthTreatItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.GROWTH_TREAT = new GrowthTreatItem(
                        new Item.Properties().food(new FoodProperties.Builder()
                                .nutrition(4).saturationMod(2.0f).build()).rarity(Rarity.UNCOMMON)));
        ITEMS.register(DeageTreatItem.ID, () ->
                org.cneko.toneko.common.mod.items.ToNekoItems.DEAGE_TREAT = new DeageTreatItem(
                        new Item.Properties().food(new FoodProperties.Builder()
                                .nutrition(4).saturationMod(2.0f).build()).rarity(Rarity.UNCOMMON)));

        CREATIVE_TABS.register("item_group", () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.toneko"))
                .icon(() -> new ItemStack(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_EARS))
                .displayItems((parameters, output) -> {
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_POTION);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_COLLECTOR);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_EARS);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_TAIL);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_PAWS);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.FURRY_BOHE);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.CATNIP);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.INFINITE_CATNIP);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.CATNIP_SANDWICH);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.CATNIP_SEED);
                    output.accept(ADVENTURER_NEKO_SPAWN_EGG.get());
                    output.accept(GHOST_NEKO_SPAWN_EGG.get());
                    output.accept(FIGHTING_NEKO_SPAWN_EGG.get());
                    output.accept(NOELLE_MAID_NEKO_SPAWN_EGG.get());
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.MUSIC_DISC_KAWAII);
                    if (ConfigUtil.IS_FOOL_DAY) {
                        output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.MUSIC_DISC_NEVER_GONNA_GIVE_YOU_UP);
                    }
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.BAZOOKA);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.LIGHTNING_BOMB);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.EXPLOSIVE_BOMB);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.ENERGY_BOMB);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.CONTRACT);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_AGGREGATOR_ITEM);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_INGOT);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_BLOCK);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_DIAMOND);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_DIAMOND_BLOCK);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_CRYSTAL);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_SMALL);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_SMALL_CHARGED);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_MEDIUM);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_MEDIUM_CHARGED);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_LARGE);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_STORAGE_LARGE_CHARGED);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_BURST);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.EVIL_NEKO_ENERGY_BURST);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.GENE_EDITOR);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.GROWTH_TREAT);
                    output.accept(org.cneko.toneko.common.mod.items.ToNekoItems.DEAGE_TREAT);
                }).build());

        EFFECTS.register("exciting", () ->
                org.cneko.toneko.common.mod.effects.ToNekoEffects.NEKO_EFFECT = new ExcitingEffect());
        EFFECTS.register("bewitched", () ->
                org.cneko.toneko.common.mod.effects.ToNekoEffects.BEWITCHED_EFFECT = new BewitchedEffect());
        EFFECTS.register("hiss_intimidation", () ->
                org.cneko.toneko.common.mod.effects.ToNekoEffects.HISS_INTIMIDATION_EFFECT =
                        new HissIntimidationEffect());

        RECIPE_TYPES.register("neko_aggregator", () ->
                org.cneko.toneko.common.mod.recipes.ToNekoRecipes.NEKO_AGGREGATOR =
                        new RecipeType<>() {
                            @Override
                            public String toString() {
                                return "toneko:neko_aggregator";
                            }
                        });
        RECIPE_SERIALIZERS.register("neko_aggregator", () ->
                org.cneko.toneko.common.mod.recipes.ToNekoRecipes.NEKO_AGGREGATOR_SERIALIZER =
                        new NekoAggregatorRecipe.Serializer());
        MENUS.register("neko_aggregator", () ->
                org.cneko.toneko.common.mod.recipes.ToNekoMenuTypes.NEKO_AGGREGATOR =
                        new MenuType<>(NekoAggregatorBlock.NekoAggregatorMenu::new, FeatureFlags.VANILLA_SET));
    }

    private ToNekoForgeContent() {
    }

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
        ENTITY_TYPES.register(bus);
        BLOCKS.register(bus);
        ITEMS.register(bus);
        CREATIVE_TABS.register(bus);
        SOUND_EVENTS.register(bus);
        EFFECTS.register(bus);
        ENCHANTMENTS.register(bus);
        MENUS.register(bus);
        RECIPE_TYPES.register(bus);
        RECIPE_SERIALIZERS.register(bus);
        bus.addListener(ToNekoForgeContent::registerEntityAttributes);
        bus.addListener(ToNekoForgeContent::addAttributesToVanillaEntities);
        bus.addListener(ToNekoForgeContent::commonSetup);
    }

    public static void registerCriteria() {
        org.cneko.toneko.common.mod.advencements.ToNekoCriteria.NEKO_LV100 =
                CriteriaTriggers.register(new NekoLevelTrigger());
        org.cneko.toneko.common.mod.advencements.ToNekoCriteria.GIFT_NEKO =
                CriteriaTriggers.register(new GiftNekoTrigger());
    }

    private static void addAttributesToVanillaEntities(EntityAttributeModificationEvent event) {
        addNekoAttributes(event, EntityType.PLAYER);
        addNekoAttributes(event, EntityType.CAT);
    }

    private static void addNekoAttributes(EntityAttributeModificationEvent event,
                                          EntityType<? extends net.minecraft.world.entity.LivingEntity> entityType) {
        event.add(entityType, org.cneko.toneko.common.mod.misc.ToNekoAttributes.NEKO_DEGREE);
        event.add(entityType, org.cneko.toneko.common.mod.misc.ToNekoAttributes.MAX_NEKO_ENERGY);
        event.add(entityType, org.cneko.toneko.common.mod.misc.ToNekoAttributes.SCALE);
    }

    private static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(org.cneko.toneko.common.mod.entities.ToNekoEntities.ADVENTURER_NEKO,
                AdventurerNeko.createAdventurerNekoAttributes().build());
        event.put(org.cneko.toneko.common.mod.entities.ToNekoEntities.CRYSTAL_NEKO,
                CrystalNekoEntity.createNekoAttributes().build());
        event.put(org.cneko.toneko.common.mod.entities.ToNekoEntities.GHOST_NEKO,
                GhostNekoEntity.createGhostNekoAttributes().build());
        event.put(org.cneko.toneko.common.mod.entities.ToNekoEntities.FIGHTING_NEKO,
                FightingNekoEntity.createFightingNekoAttributes().build());
        event.put(org.cneko.toneko.common.mod.entities.ToNekoEntities.MOUFLET_NEKO_BOSS,
                MoufletNekoBoss.createMoufletNekoAttributes().build());
        event.put(org.cneko.toneko.common.mod.entities.ToNekoEntities.RAVENN_ENTITY,
                RavennEntity.createRavennAttributes().build());
        event.put(org.cneko.toneko.common.mod.entities.ToNekoEntities.NOELLE_MAID_NEKO,
                NoelleMaidNekoEntity.createNoelleAttributes().build());
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            org.cneko.toneko.common.mod.entities.ToNekoEntities.init();
            NekoSkinRegistry.register(org.cneko.toneko.common.mod.entities.ToNekoEntities.ADVENTURER_NEKO,
                    AdventurerNeko.nekoSkins);
            NekoSkinRegistry.register(org.cneko.toneko.common.mod.entities.ToNekoEntities.GHOST_NEKO,
                    GhostNekoEntity.nekoSkins);
            NekoSkinRegistry.register(org.cneko.toneko.common.mod.entities.ToNekoEntities.FIGHTING_NEKO,
                    FightingNekoEntity.NEKO_SKINS);
            NekoSkinRegistry.register(org.cneko.toneko.common.mod.entities.ToNekoEntities.MOUFLET_NEKO_BOSS,
                    MoufletNekoBoss.NEKO_SKINS);
            NekoSkinRegistry.register(org.cneko.toneko.common.mod.entities.ToNekoEntities.NOELLE_MAID_NEKO,
                    FightingNekoEntity.NEKO_SKINS);
            org.cneko.toneko.common.mod.entities.ToNekoEntities.registerBiomeSpawns(
                    org.cneko.toneko.common.mod.entities.ToNekoEntities.ADVENTURER_NEKO,
                    org.cneko.toneko.common.mod.entities.ToNekoEntities.GHOST_NEKO,
                    org.cneko.toneko.common.mod.entities.ToNekoEntities.CRYSTAL_NEKO,
                    org.cneko.toneko.common.mod.entities.ToNekoEntities.FIGHTING_NEKO,
                    org.cneko.toneko.common.mod.entities.ToNekoEntities.NOELLE_MAID_NEKO);
        });
    }

    private static Enchantment energyBurstEnchantment(Enchantment.Rarity rarity, int maxLevel,
                                                       int baseCost, int perLevelCost) {
        return new ItemEnchantment(rarity, maxLevel, baseCost, perLevelCost,
                stack -> stack.is(org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_ENERGY_BURST)
                        || stack.is(org.cneko.toneko.common.mod.items.ToNekoItems.EVIL_NEKO_ENERGY_BURST),
                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND);
    }

    /** 1.20.1 equivalent of the exact-item, data-driven enchantments used by 1.21. */
    private static final class ItemEnchantment extends Enchantment {
        private final int maxLevel;
        private final int baseCost;
        private final int perLevelCost;
        private final Predicate<ItemStack> supportedItem;

        private ItemEnchantment(Enchantment.Rarity rarity, int maxLevel, int baseCost, int perLevelCost,
                                Predicate<ItemStack> supportedItem, EquipmentSlot... slots) {
            super(rarity, EnchantmentCategory.BREAKABLE, slots);
            this.maxLevel = maxLevel;
            this.baseCost = baseCost;
            this.perLevelCost = perLevelCost;
            this.supportedItem = supportedItem;
        }

        @Override
        public int getMaxLevel() {
            return maxLevel;
        }

        @Override
        public int getMinCost(int level) {
            return baseCost + Math.max(0, level - 1) * perLevelCost;
        }

        @Override
        public int getMaxCost(int level) {
            return getMinCost(level) + 15;
        }

        @Override
        public boolean canEnchant(ItemStack stack) {
            return supportedItem.test(stack);
        }
    }
}
