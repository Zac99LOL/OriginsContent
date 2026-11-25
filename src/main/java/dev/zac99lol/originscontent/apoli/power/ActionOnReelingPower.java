package dev.zac99lol.originscontent.apoli.power;

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

public class ActionOnReelingPower extends CooldownPower {
    private final boolean onEntityHit;
    private final Consumer<Entity> entityAction;
    private final Predicate<Entity> entityCondition;

    public ActionOnReelingPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, boolean onEntityHit, Consumer<Entity> entityAction, Predicate<Entity> entityCondition) {
        super(type, entity, cooldownDuration, hudRender);
        this.onEntityHit = onEntityHit;
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

    public boolean onEntityHit() {
        return onEntityHit && canUse();
    }

    public static PowerFactory getFactory() {
        return new PowerFactory<ActionOnReelingPower>(OriginsContent.id("arsenal.action_on_reeling"),
            new SerializableData()
                .add("cooldown", SerializableDataTypes.INT, 1)
                .add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
                .add("on_entity_hit", SerializableDataTypes.BOOLEAN, false)
                .add("entity_action", ApoliDataTypes.ENTITY_ACTION)
                .add("entity_condition", ApoliDataTypes.ENTITY_CONDITION, null),
            data -> (type, entity) -> new ActionOnReelingPower(type, entity,
                data.get("cooldown"),
                data.get("hud_render"),
                data.get("on_entity_hit"),
                data.get("entity_action"),
                data.get("entity_condition")
            )
        );
    }
}
