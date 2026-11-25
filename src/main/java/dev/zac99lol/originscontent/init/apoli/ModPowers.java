package dev.zac99lol.originscontent.init.apoli;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.apoli.power.*;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.PowerFactorySupplier;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public interface ModPowers {
    @SuppressWarnings("unchecked")
    static void initialize() {
        register(() -> Power.createSimpleFactory(CancelTotemsPower::new, OriginsContent.id("cancel_totems")));
        register(() -> Power.createSimpleFactory(DisableOffhandPower::new, OriginsContent.id("disable_offhand")));
        register(CancelInventoryActionsPower::getFactory);
        register(ReflectDmgPower::getFactory);
        register(OverrideHotbarPower::getFactory);
        register(() -> Power.createSimpleFactory(TickingEntityPower::new, OriginsContent.id("ticking_entity")));
        register(ActionOnThunderPower::getFactory);
        register(SprintingHungerPower::getFactory);
        register(PreventChatPower::getFactory);
        register(PreventDivingPower::getFactory);
        register(ActionOnReelingPower::getFactory);
        register(OverrideHudTexturePower::getFactory);
    }

    private static void register(PowerFactory<?> powerFactory) {
        Registry.register(ApoliRegistries.POWER_FACTORY, powerFactory.getSerializerId(), powerFactory);
    }

    private static void register(PowerFactorySupplier<?> factorySupplier) {
        register(factorySupplier.createFactory());
    }
}
