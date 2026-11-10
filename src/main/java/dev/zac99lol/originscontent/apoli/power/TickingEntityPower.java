package dev.zac99lol.originscontent.apoli.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.LivingEntity;

public class TickingEntityPower extends Power {
    public TickingEntityPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity nullEntity = null;
        //noinspection DataFlowIssue
        nullEntity.tick(); // stfu ik it will cause NullPointerException :sob:
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
            OriginsContent.id("ticking_entity"),
            new SerializableData(),
            data -> TickingEntityPower::new
        );
    }
}
