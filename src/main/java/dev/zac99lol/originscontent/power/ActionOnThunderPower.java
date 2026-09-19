package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActionOnThunderPower extends CooldownPower {
    private final Consumer<Entity> entityAction;
    private final Predicate<Entity> entityCondition;

    public ActionOnThunderPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> entityAction, Predicate<Entity> entityCondition) {
        super(type, entity, cooldownDuration, hudRender);
        this.entityAction = entityAction;
        this.entityCondition = entityCondition;
    }

    public void activate() {
        if (canUse()) {
            if (entityCondition == null || entityCondition.test(entity)) {
                entityAction.accept(entity);
                use();
            }
        }
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<ActionOnThunderPower>(
            OriginsContent.id("action_on_thunder"),
            new SerializableData()
                .add("cooldown", SerializableDataTypes.INT, 1)
                .add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
                .add("entity_action", ApoliDataTypes.ENTITY_ACTION)
                .add("entity_condition", ApoliDataTypes.ENTITY_CONDITION, null),
            data -> (type, entity) -> new ActionOnThunderPower(type, entity,
                data.getInt("cooldown"),
                data.get("hud_render"),
                data.get("entity_action"),
                data.get("entity_condition"))
        );
    }
}
