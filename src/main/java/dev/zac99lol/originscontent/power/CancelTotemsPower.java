package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

/**
 * Marker power - while active, prevents this entity from consuming totems
 * of undying. See LivingEntityMixin for the actual behavior. No
 * custom data beyond Apoli's standard "condition" field (via allowCondition()).
 */
public class CancelTotemsPower extends Power {
    public CancelTotemsPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory<?> getFactory() {
        return (PowerFactory<?>) Power.createSimpleFactory(
            CancelTotemsPower::new,
            new Identifier(OriginsContent.MOD_ID, "cancel_totems")
        );
    }
}
