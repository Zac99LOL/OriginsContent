package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

public class ReflectDmgPower extends Power {

    private final ReflectConfig reflect;
    private final ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition;
    private final boolean immune;
    private final ActionFactory<Pair<Entity, Entity>>.Instance actionOnParry;

    public ReflectDmgPower(PowerType<?> type, LivingEntity entity, ReflectConfig reflect,
                      ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition,
                      boolean immune,
                      ActionFactory<Pair<Entity, Entity>>.Instance actionOnParry) {
        super(type, entity);
        this.reflect = reflect;
        this.damageCondition = damageCondition;
        this.immune = immune;
        this.actionOnParry = actionOnParry;
    }

    public void onSuccessfulParry(Entity attacker) {
        if (actionOnParry != null) {
            actionOnParry.accept(new Pair<>(entity, attacker));
        }
    }

    public boolean matchesDamageCondition(DamageSource source, float amount) {
        return damageCondition == null || damageCondition.test(new Pair<>(source, amount));
    }

    public boolean matchesReflectCondition(DamageSource source, float amount) {
        return reflect.damageCondition == null || reflect.damageCondition.test(new Pair<>(source, amount));
    }

    public float applyReflectModifier(float amount) {
        return reflect.damageModifier == null ? amount : (float) reflect.damageModifier.apply(entity, amount);
    }

    public Identifier getReflectDamageType() {
        return reflect.damageType;
    }

    public boolean isImmune() {
        return immune;
    }

    public record ReflectConfig(
        ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition,
        Modifier damageModifier,
        Identifier damageType
    ) {}

    private static final SerializableDataType<ReflectConfig> REFLECT_CONFIG =
        SerializableDataType.compound(
            ReflectConfig.class,
            new SerializableData()
                .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                .add("damage_modifier", Modifier.DATA_TYPE, null)
                .add("damage_type", SerializableDataTypes.IDENTIFIER, DamageTypes.PLAYER_ATTACK.getValue()),
            data -> new ReflectConfig(
                data.get("damage_condition"),
                data.get("damage_modifier"),
                data.get("damage_type")
            ),
            (data, inst) -> {
                SerializableData.Instance dataInst = data.new Instance();
                dataInst.set("damage_condition", inst.damageCondition());
                dataInst.set("damage_modifier", inst.damageModifier());
                dataInst.set("damage_type", inst.damageType());
                return dataInst;
            });

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
            OriginsContent.id("reflect_dmg"),
            new SerializableData()
                .add("reflect", REFLECT_CONFIG)
                .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                .add("immune", SerializableDataTypes.BOOLEAN, true)
                .add("action_on_parry", ApoliDataTypes.BIENTITY_ACTION, null),
            data -> (type, entity) -> new ReflectDmgPower(
                type, entity,
                data.get("reflect"),
                data.get("damage_condition"),
                data.getBoolean("immune"),
                data.get("action_on_parry")
            )
        ).allowCondition();
    }
}