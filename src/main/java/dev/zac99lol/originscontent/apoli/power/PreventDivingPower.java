package dev.zac99lol.originscontent.apoli.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.function.Consumer;

public class PreventDivingPower extends Power {
    private final Consumer<Entity> entityAction;

    public PreventDivingPower(PowerType<?> type, LivingEntity entity, Consumer<Entity> entityAction) {
        super(type, entity);
        this.entityAction = entityAction;
    }

    public void executeAction() {
        if (entityAction != null) {
            entityAction.accept(entity);
        }
    }

    @SuppressWarnings("unchecked")
    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(OriginsContent.id("defile.prevent_diving"),
            new SerializableData().add("entity_action", ApoliDataTypes.ENTITY_ACTION, null),
            data -> (type, entity) -> new PreventDivingPower(type, entity,
                (ActionFactory<Entity>.Instance) data.get("entity_action")))
            .allowCondition();
    }
}
