package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.power.*;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public abstract class ModPowers {
    public static void init() {
        Registry.register(ApoliRegistries.POWER_FACTORY, OriginsContent.id("cancel_totems"), CancelTotemsPower.getFactory());
        Registry.register(ApoliRegistries.POWER_FACTORY, OriginsContent.id("disable_offhand"), DisableOffhandPower.getFactory());
        Registry.register(ApoliRegistries.POWER_FACTORY, OriginsContent.id("cancel_inventory_actions"), CancelInventoryActionsPower.getFactory());
        Registry.register(ApoliRegistries.POWER_FACTORY, OriginsContent.id("parry"), ParryPower.getFactory());
        Registry.register(ApoliRegistries.POWER_FACTORY, OriginsContent.id("reflect_dmg"), ReflectDmgPower.getFactory());
    }
}
