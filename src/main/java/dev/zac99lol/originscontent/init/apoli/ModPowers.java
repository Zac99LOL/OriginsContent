package dev.zac99lol.originscontent.init.apoli;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.apoli.power.*;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModPowers {
    Map<PowerFactory<?>, Identifier> POWERS = new LinkedHashMap<>();
    PowerFactory<?> CANCEL_TOTEMS = createPower("cancel_totems", CancelTotemsPower.getFactory());
    PowerFactory<?> DISABLE_OFFHAND = createPower("disable_offhand", DisableOffhandPower.getFactory());
    PowerFactory<?> CANCEL_INVENTORY_ACTIONS = createPower("cancel_inventory_actions", CancelInventoryActionsPower.getFactory());
    PowerFactory<?> REFLECT_DMG = createPower("reflect_dmg", ReflectDmgPower.getFactory());
    PowerFactory<?> OVERRIDE_HOTBAR = createPower("override_hotbar", OverrideHotbarPower.getFactory());
    PowerFactory<?> TICKING_ENTITY = createPower("ticking_entity", TickingEntityPower.getFactory());
    PowerFactory<?> ACTION_ON_THUNDER = createPower("defile.action_on_thunder", ActionOnThunderPower.getFactory());
    PowerFactory<?> SPRINTING_HUNGER = createPower("sprinting_hunger", SprintingHungerPower.getFactory());
    PowerFactory<?> PREVENT_CHAT = createPower("prevent_chat", PreventChatPower.getFactory());
    PowerFactory<?> DEFILE_PREVENT_DIVING = createPower("defile.prevent_diving", PreventDivingPower.getFactory());

    private static PowerFactory<?> createPower(String id, PowerFactory<?> powerFactory) {
        POWERS.put(powerFactory, OriginsContent.id(id));
        return powerFactory;
    }

    static void initialize() {
        POWERS.keySet().forEach(power -> Registry.register(ApoliRegistries.POWER_FACTORY, POWERS.get(power), power));
    }
}
