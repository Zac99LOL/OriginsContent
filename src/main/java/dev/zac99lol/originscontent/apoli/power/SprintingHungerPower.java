package dev.zac99lol.originscontent.apoli.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class SprintingHungerPower extends Power {
    private final float minimumSprintingHunger;

    public SprintingHungerPower(PowerType<?> type, LivingEntity entity, float minimumSprintingHunger) {
        super(type, entity);
        this.minimumSprintingHunger = minimumSprintingHunger;
    }

    public float getMinimumSprintingHunger() {
        return minimumSprintingHunger;
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(OriginsContent.id("sprinting_hunger"),
            new SerializableData()
                .add("minimum", SerializableDataTypes.FLOAT),
            data -> (type, entity) -> new SprintingHungerPower(type, entity,
                data.getFloat("minimum")));
    }
}
