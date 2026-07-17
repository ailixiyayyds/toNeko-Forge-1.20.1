package org.cneko.toneko.fabric.entities;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import org.cneko.toneko.common.mod.api.NekoNameRegistry;
import org.cneko.toneko.common.mod.api.NekoSkinRegistry;
import org.cneko.toneko.common.mod.entities.*;
import org.cneko.toneko.common.mod.entities.boss.mouflet.MoufletNekoBoss;
import org.cneko.toneko.common.util.ConfigUtil;

import java.util.Set;

import static org.cneko.toneko.common.Bootstrap.MODID;
import static org.cneko.toneko.common.mod.entities.ToNekoEntities.*;

public class ToNekoEntities {
    public static void init(){
        ADVENTURER_NEKO = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                ADVENTURER_NEKO_ID,
                FabricEntityTypeBuilder.createMob().spawnGroup(MobCategory.CREATURE).entityFactory(AdventurerNeko::new)
                        .defaultAttributes(AdventurerNeko::createAdventurerNekoAttributes)
                        .dimensions(EntityDimensions.fixed(0.5f, 1.7f)).build()
        );
        CRYSTAL_NEKO = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                CRYSTAL_NEKO_ID,
                FabricEntityTypeBuilder.createMob().spawnGroup(MobCategory.CREATURE).entityFactory(CrystalNekoEntity::new)
                        .defaultAttributes(CrystalNekoEntity::createNekoAttributes)
                        .dimensions(EntityDimensions.fixed(0.5f, 1.7f)).trackRangeBlocks(8).build()
        );
        GHOST_NEKO = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                GHOST_NEKO_ID,
                FabricEntityTypeBuilder.createMob().spawnGroup(MobCategory.CREATURE).entityFactory(GhostNekoEntity::new)
                        .defaultAttributes(GhostNekoEntity::createGhostNekoAttributes)
                        .dimensions(EntityDimensions.fixed(0.4f, 1.2f)).trackRangeBlocks(8).build()
        );
        FIGHTING_NEKO  = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                FIGHTING_NEKO_ID,
                FabricEntityTypeBuilder.createMob().spawnGroup(MobCategory.CREATURE).entityFactory(FightingNekoEntity::new)
                        .defaultAttributes(FightingNekoEntity::createFightingNekoAttributes)
                        .dimensions(EntityDimensions.fixed(0.5f, 1.7f)).build()
        );
        MOUFLET_NEKO_BOSS = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                MOUFLET_NEKO_BOSS_ID,
                FabricEntityTypeBuilder.createMob().spawnGroup(MobCategory.MONSTER).entityFactory(MoufletNekoBoss::new)
                        .defaultAttributes(MoufletNekoBoss::createMoufletNekoAttributes)
                        .dimensions(EntityDimensions.fixed(0.5f, 1.7f)).trackRangeBlocks(8).build()
        );
        RAVENN_ENTITY = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                RAVENN_ID,
                FabricEntityTypeBuilder.createMob().spawnGroup(MobCategory.MONSTER).entityFactory(RavennEntity::new)
                        .defaultAttributes(RavennEntity::createRavennAttributes)
                        .dimensions(EntityDimensions.fixed(0.5f, 1.7f)).trackRangeBlocks(8).build()
        );
        NOELLE_MAID_NEKO = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                NOELLE_MAID_NEKO_ID,
                FabricEntityTypeBuilder.createMob().spawnGroup(MobCategory.CREATURE).entityFactory(NoelleMaidNekoEntity::new)
                        .defaultAttributes(NoelleMaidNekoEntity::createNoelleAttributes)
                        .dimensions(EntityDimensions.fixed(0.5f, 1.7f)).build()
        );

        AMMUNITION_ENTITY = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                AMMUNITION_ENTITY_ID,
                EntityType.Builder.of(AmmunitionEntity::new, MobCategory.MISC)
                        .sized(0.25f,0.25f).build(AMMUNITION_ENTITY_ID.toString())
        );

        org.cneko.toneko.common.mod.entities.ToNekoEntities.init();

        // 注册皮肤
        NekoSkinRegistry.register(ADVENTURER_NEKO,AdventurerNeko.nekoSkins);
        NekoSkinRegistry.register(GHOST_NEKO,GhostNekoEntity.nekoSkins);
        NekoSkinRegistry.register(FIGHTING_NEKO, FightingNekoEntity.NEKO_SKINS);
        NekoSkinRegistry.register(MOUFLET_NEKO_BOSS, MoufletNekoBoss.NEKO_SKINS);
        NekoSkinRegistry.register(NOELLE_MAID_NEKO, FightingNekoEntity.NEKO_SKINS);

        // 注册群系生成（委托 common 方法）
        registerBiomeSpawns(ADVENTURER_NEKO, GHOST_NEKO, CRYSTAL_NEKO, FIGHTING_NEKO, NOELLE_MAID_NEKO);
    }
}
