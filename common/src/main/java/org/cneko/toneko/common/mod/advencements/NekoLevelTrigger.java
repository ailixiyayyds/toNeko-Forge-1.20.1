package org.cneko.toneko.common.mod.advencements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.cneko.toneko.common.mod.entities.INeko;

import static org.cneko.toneko.common.Bootstrap.MODID;

public class NekoLevelTrigger extends SimpleCriterionTrigger<NekoLevelTrigger.TriggerInstance> {
    private static final ResourceLocation ID = new ResourceLocation(MODID, "neko_lv100");

    public ResourceLocation getId() { return ID; }

    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new TriggerInstance(player, json.get("level").getAsDouble());
    }

    public void trigger(ServerPlayer player) {
        trigger(player, triggerInstance -> triggerInstance.matches(((INeko) player).getNekoLevel()));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final double level;
        public TriggerInstance(ContextAwarePredicate player, double level) {
            super(ID, player);
            this.level = level;
        }
        public boolean matches(double value) { return value >= this.level; }
        public static TriggerInstance hasLevel(double level) { return new TriggerInstance(ContextAwarePredicate.ANY, level); }
        public JsonObject serializeToJson(net.minecraft.advancements.critereon.SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("level", level);
            return json;
        }
    }
}
