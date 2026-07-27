package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.minecraft.entity.LivingEntity;

public class CancelTotemsPower extends Power {
    public CancelTotemsPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory<?> getFactory() {
        return (PowerFactory<?>) Power.createSimpleFactory(
            CancelTotemsPower::new,
            OriginsContent.id("cancel_totems")
        );
    }
}
