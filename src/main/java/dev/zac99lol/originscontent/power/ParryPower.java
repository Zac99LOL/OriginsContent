package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class ParryPower extends CooldownPower implements Active {

    private final int parryWindow;
    private final boolean cooldownOnWhiff;
    private final ReflectConfig reflect;
    private final ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition;
    private final boolean immune;
    private final ActionFactory<Pair<Entity, Entity>>.Instance actionOnParry;
    private final ActionFactory<Entity>.Instance actionOnWhiff;

    private Active.Key key;
    private long windowOpenedTick = -1;

    public ParryPower(PowerType<?> type, LivingEntity entity, int parryWindow, int cooldown,
                      HudRender hudRender, boolean cooldownOnWhiff, ReflectConfig reflect,
                      ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition,
                      boolean immune,
                      ActionFactory<Pair<Entity, Entity>>.Instance actionOnParry,
                      ActionFactory<Entity>.Instance actionOnWhiff) {
        super(type, entity, cooldown, hudRender);
        this.parryWindow = parryWindow;
        this.cooldownOnWhiff = cooldownOnWhiff;
        this.reflect = reflect;
        this.damageCondition = damageCondition;
        this.immune = immune;
        this.actionOnParry = actionOnParry;
        this.actionOnWhiff = actionOnWhiff;
    }

    @Override
    public void onUse() {
        if (!canUse()) {
            return;
        }
        windowOpenedTick = entity.getEntityWorld().getTime();
        if (cooldownOnWhiff) {
            use();
        }
    }

    @Override
    public Key getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = key;
    }

    public boolean isWindowActive() {
        return windowOpenedTick >= 0
            && entity.getEntityWorld().getTime() - windowOpenedTick <= parryWindow;
    }

    public void onSuccessfulParry(Entity attacker) {
        windowOpenedTick = -1;
        if (!cooldownOnWhiff) {
            use();
        }
        if (actionOnParry != null) {
            actionOnParry.accept(new Pair<>(entity, attacker));
        }
    }

    public boolean matchesDamageCondition(DamageSource source, float amount) {
        return damageCondition == null || damageCondition.test(new Pair<>(source, amount));
    }

    public boolean shouldReflect() {
        return reflect.shouldReflect;
    }

    public boolean matchesReflectCondition(DamageSource source, float amount) {
        return reflect.damageCondition == null || reflect.damageCondition.test(new Pair<>(source, amount));
    }

    public float applyReflectModifier(float amount) {
        return reflect.damageModifier == null ? amount : reflect.damageModifier.apply(amount);
    }

    public Identifier getReflectDamageType() {
        return reflect.damageType;
    }

    public boolean isImmune() {
        return immune;
    }

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public void tick() {
        if (windowOpenedTick < 0) {
            return;
        }
        long now = entity.getEntityWorld().getTime();
        if (now - windowOpenedTick > parryWindow) {
            // window expired with no successful parry
            windowOpenedTick = -1;
            if (actionOnWhiff != null) {
                actionOnWhiff.accept(entity);
            }
        }
    }

    public record ReflectModifier(EntityAttributeModifier.Operation operation, double value) {
        public float apply(float base) {
            return switch (operation) {
                case ADDITION -> (float) (base + value);
                case MULTIPLY_BASE, MULTIPLY_TOTAL -> (float) (base * value);
            };
        }
    }

    public record ReflectConfig(
        boolean shouldReflect,
        ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition,
        ReflectModifier damageModifier,
        Identifier damageType
    ) {}

    private static final SerializableDataType<ReflectModifier> REFLECT_MODIFIER =
        SerializableDataType.compound(
            ReflectModifier.class,
            new SerializableData()
                .add("operation", SerializableDataTypes.MODIFIER_OPERATION, EntityAttributeModifier.Operation.MULTIPLY_TOTAL)
                .add("value", SerializableDataTypes.DOUBLE, 1.0),
            data -> new ReflectModifier(data.get("operation"), data.getDouble("value")),
            (data, inst) -> {
                SerializableData.Instance dataInst = data.new Instance();
                dataInst.set("operation", inst.operation());
                dataInst.set("value", inst.value());
                return dataInst;
            });

    private static final SerializableDataType<ReflectConfig> REFLECT_CONFIG =
        SerializableDataType.compound(
            ReflectConfig.class,
            new SerializableData()
                .add("should_reflect", SerializableDataTypes.BOOLEAN)
                .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                .add("damage_modifier", REFLECT_MODIFIER, null)
                .add("damage_type", SerializableDataTypes.IDENTIFIER, null),
            data -> new ReflectConfig(
                data.getBoolean("should_reflect"),
                data.get("damage_condition"),
                data.get("damage_modifier"),
                data.get("damage_type")
            ),
            (data, inst) -> {
                SerializableData.Instance dataInst = data.new Instance();
                dataInst.set("should_reflect", inst.shouldReflect());
                dataInst.set("damage_condition", inst.damageCondition());
                dataInst.set("damage_modifier", inst.damageModifier());
                dataInst.set("damage_type", inst.damageType());
                return dataInst;
            });

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
            OriginsContent.id("parry"),
            new SerializableData()
                .add("parry_window", SerializableDataTypes.INT)
                .add("key", ApoliDataTypes.BACKWARDS_COMPATIBLE_KEY, new Active.Key())
                .add("reflect", REFLECT_CONFIG)
                .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                .add("immune", SerializableDataTypes.BOOLEAN, true)
                .add("cooldown", SerializableDataTypes.INT)
                .add("hud_render", ApoliDataTypes.HUD_RENDER, HudRender.DONT_RENDER)
                .add("cooldown_on_whiff", SerializableDataTypes.BOOLEAN, true)
                .add("action_on_parry", ApoliDataTypes.BIENTITY_ACTION)
                .add("action_on_whiff", ApoliDataTypes.ENTITY_ACTION),
            data -> (type, entity) -> {
                ParryPower power = new ParryPower(
                    type, entity,
                    data.getInt("parry_window"),
                    data.getInt("cooldown"),
                    data.get("hud_render"),
                    data.getBoolean("cooldown_on_whiff"),
                    data.get("reflect"),
                    data.get("damage_condition"),
                    data.getBoolean("immune"),
                    data.get("action_on_parry"),
                    data.get("action_on_whiff")
                );
                power.setKey(data.get("key"));
                return power;
            }
        ).allowCondition();
    }
}