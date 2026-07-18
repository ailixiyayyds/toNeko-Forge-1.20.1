package org.cneko.toneko.common.mod.misc;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import org.jetbrains.annotations.NotNull;

import static org.cneko.toneko.common.Bootstrap.MODID;

public class ToNekoAttributes {
    public static final ResourceLocation NEKO_DEGREE_ID = new ResourceLocation(MODID, "neko.degree");
    public static final @NotNull Attribute NEKO_DEGREE = new RangedAttribute("attribute.name.neko.degree",
        1.0, 0, 100.0
        ).setSyncable(true);
    public static final ResourceLocation MAX_NEKO_ENERGY_ID = new ResourceLocation(MODID, "neko.max_energy");
    public static final @NotNull Attribute MAX_NEKO_ENERGY = new RangedAttribute("attribute.name.neko.max_energy",
        1000.0, 0.0, 100000.0
        ).setSyncable(true);
    public static final ResourceLocation SCALE_ID = new ResourceLocation(MODID, "neko.scale");
    public static final @NotNull Attribute SCALE =
            new RangedAttribute("attribute.name.neko.scale", 1.0, 0.1, 4.0).setSyncable(true);

    public static void init() {
        // Fabric calls this platform initializer. Forge registers the same
        // instances through DeferredRegister and intentionally does not call it.
        Registry.register(BuiltInRegistries.ATTRIBUTE, NEKO_DEGREE_ID, NEKO_DEGREE);
        Registry.register(BuiltInRegistries.ATTRIBUTE, MAX_NEKO_ENERGY_ID, MAX_NEKO_ENERGY);
        Registry.register(BuiltInRegistries.ATTRIBUTE, SCALE_ID, SCALE);
    }
}
