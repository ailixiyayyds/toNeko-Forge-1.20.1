package org.cneko.toneko.common.mod.misc.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;

public class ToNekoAttributesImpl {
    public static Attribute register(ResourceLocation id, Attribute attribute) {
        return Registry.register(BuiltInRegistries.ATTRIBUTE,id, attribute);
    }
}
