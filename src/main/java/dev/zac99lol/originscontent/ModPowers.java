package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.power.CancelInventoryActionsPower;
import dev.zac99lol.originscontent.power.CancelTotemsPower;
import dev.zac99lol.originscontent.power.DisableOffhandPower;
import dev.zac99lol.originscontent.power.ParryPower;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static dev.zac99lol.originscontent.OriginsContent.MOD_ID;

public abstract class ModPowers {
    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, new Identifier(MOD_ID, "cancel_totems"), CancelTotemsPower.getFactory());
        Registry.register(ApoliRegistries.POWER_FACTORY, new Identifier(MOD_ID, "disable_offhand"), DisableOffhandPower.getFactory());
        Registry.register(ApoliRegistries.POWER_FACTORY, new Identifier(MOD_ID, "cancel_inventory_actions"), CancelInventoryActionsPower.getFactory());
        Registry.register(ApoliRegistries.POWER_FACTORY, new Identifier(MOD_ID, "parry"), ParryPower.getFactory());
    }
}
