package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class PreventSprintingParticlesPower extends Power {
    public PreventSprintingParticlesPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
            OriginsContent.id("prevent_sprinting_particles"),
            new SerializableData(),
            data -> (type, entity) -> new PreventSprintingParticlesPower(
                type, entity
            )
        ).allowCondition();
    }
}
