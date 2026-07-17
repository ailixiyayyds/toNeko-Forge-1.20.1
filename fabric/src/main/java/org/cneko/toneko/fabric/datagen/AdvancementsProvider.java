package org.cneko.toneko.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;
import org.cneko.toneko.common.mod.advencements.GiftNekoTrigger;
import org.cneko.toneko.common.mod.advencements.NekoLevelTrigger;
import org.cneko.toneko.common.mod.items.ToNekoItems;

import java.util.function.Consumer;

import static org.cneko.toneko.common.Bootstrap.MODID;
import static org.cneko.toneko.common.mod.util.TextUtil.translatable;
import static org.cneko.toneko.fabric.items.ToNekoItems.NEKO_COLLECTOR;

public class AdvancementsProvider extends FabricAdvancementProvider {
    public static Advancement NEKO_ATTRACTING;
    public static Advancement GOT_NEKO_POTION;
    public static Advancement NEKO_ARMOR;
    public static Advancement CATNIP;
    public static Advancement FIRST_GIFT;
    public static Advancement NEKO_LV100;

    protected AdvancementsProvider(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        NEKO_ATTRACTING = Advancement.Builder.advancement()
                .display(
                        ToNekoItems.NEKO_EARS, // 以猫耳朵作为图标
                        translatable("advancements.toneko.root.title"),
                        translatable("advancements.toneko.root.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.GOAL,
                        true, // 获得时显示在屏幕右上
                        true, // 获得发送到聊天
                        false // 不隐藏进度
                )
                .addCriterion("neko_attracting", InventoryChangeTrigger.TriggerInstance.hasItems(NEKO_COLLECTOR))
                .save(consumer, MODID+"/root");
        GOT_NEKO_POTION = Advancement.Builder.advancement()
                .display(
                        ToNekoItems.NEKO_POTION, // 以猫娘药水作为图标
                        translatable("advancements.toneko.got_neko_potion.title"),
                        translatable("advancements.toneko.got_neko_potion.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.GOAL,
                        true, // 获得时显示在屏幕右上
                        true, // 获得发送到聊天
                        false // 不隐藏进度
                )
                .addCriterion("got_neko_potion", InventoryChangeTrigger.TriggerInstance.hasItems(ToNekoItems.NEKO_POTION))
                .parent(NEKO_ATTRACTING)
                .save(consumer, MODID+"/got_neko_potion");
        NEKO_ARMOR = Advancement.Builder.advancement()
                .display(
                        ToNekoItems.NEKO_EARS,
                        translatable("advancements.toneko.neko_armor.title"),
                        translatable("advancements.toneko.neko_armor.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.GOAL,
                        true, // 获得时显示在屏幕右上
                        true, // 获得发送到聊天
                        false // 不隐藏进度
                )
                .addCriterion("neko_armor",InventoryChangeTrigger.TriggerInstance.hasItems(ToNekoItems.NEKO_TAIL,ToNekoItems.NEKO_EARS))
                .parent(NEKO_ATTRACTING)
                .save(consumer,MODID+"/neko_armor");
        CATNIP = Advancement.Builder.advancement()
                .display(
                        ToNekoItems.CATNIP,
                        translatable("advancements.toneko.catnip.title"),
                        translatable("advancements.toneko.catnip.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.GOAL,
                        true, // 获得时显示在屏幕右上
                        true,
                        false
                )
                .addCriterion("catnip",InventoryChangeTrigger.TriggerInstance.hasItems(ToNekoItems.CATNIP))
                .parent(GOT_NEKO_POTION)
                .save(consumer,MODID+"/catnip");

        FIRST_GIFT = Advancement.Builder.advancement()
                .display(
                        ToNekoItems.CATNIP_SANDWICH,
                        translatable("advancements.toneko.first_gift.title"),
                        translatable("advancements.toneko.first_gift.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("first_gift", GiftNekoTrigger.TriggerInstance.create())
                .parent(CATNIP)
                .save(consumer,MODID+"/first_gift");

        NEKO_LV100 = Advancement.Builder.advancement()
                .display(
                        ToNekoItems.NEKO_TAIL,
                        translatable("advancements.toneko.neko_lv100.title"),
                        translatable("advancements.toneko.neko_lv100.description"),
                        new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png"),
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("neko_lv100", NekoLevelTrigger.TriggerInstance.hasLevel(100))
                .parent(GOT_NEKO_POTION)
                .save(consumer,MODID+"/neko_lv100");

    }
}
