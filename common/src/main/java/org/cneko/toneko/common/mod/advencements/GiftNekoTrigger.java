package org.cneko.toneko.common.mod.advencements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import static org.cneko.toneko.common.Bootstrap.MODID;

public class GiftNekoTrigger extends SimpleCriterionTrigger<GiftNekoTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(MODID, "gift_neko");

    public ResourceLocation getId() { return ID; }

    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new TriggerInstance(player);
    }

    public void trigger(ServerPlayer player) { this.trigger(player, instance -> true); }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate player) { super(ID, player); }
        public static TriggerInstance create() { return new TriggerInstance(ContextAwarePredicate.ANY); }
    }
}
