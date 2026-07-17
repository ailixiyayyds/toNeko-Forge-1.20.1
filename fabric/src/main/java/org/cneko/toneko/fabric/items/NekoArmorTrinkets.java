package org.cneko.toneko.fabric.items;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.cneko.toneko.common.mod.items.NekoArmor;

import static org.cneko.toneko.common.Bootstrap.LOGGER;
import static org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_EARS;
import static org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_PAWS;
import static org.cneko.toneko.common.mod.items.ToNekoItems.NEKO_TAIL;

public final class NekoArmorTrinkets {
    private NekoArmorTrinkets() {
    }

    public static void init() {
        LOGGER.info("Trinkets detected, registering Neko Armors as trinkets");
        NekoEarsTrinketItem ears = new NekoEarsTrinketItem();
        NekoTailTrinketItem tail = new NekoTailTrinketItem();
        NekoPawsTrinketItem paws = new NekoPawsTrinketItem();
        NEKO_EARS = ears;
        NEKO_TAIL = tail;
        NEKO_PAWS = paws;
        TrinketsApi.registerTrinket(ears, ears);
        TrinketsApi.registerTrinket(tail, tail);
        TrinketsApi.registerTrinket(paws, paws);
    }

    public static class NekoTailTrinketItem extends NekoArmor.NekoTailItem implements Trinket {
        public NekoTailTrinketItem() {
            super(ToNekoArmorMaterials.NEKO);
        }

        @Override
        public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
            return true;
        }
    }

    public static class NekoEarsTrinketItem extends NekoArmor.NekoEarsItem implements Trinket {
        public NekoEarsTrinketItem() {
            super(ToNekoArmorMaterials.NEKO);
        }

        @Override
        public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
            return true;
        }
    }

    public static class NekoPawsTrinketItem extends NekoArmor.NekoPawsItem implements Trinket {
        public NekoPawsTrinketItem() {
            super(ToNekoArmorMaterials.NEKO);
        }

        @Override
        public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
            return true;
        }
    }
}
