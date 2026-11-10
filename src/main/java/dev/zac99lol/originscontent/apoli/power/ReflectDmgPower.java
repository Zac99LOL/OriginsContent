package dev.zac99lol.originscontent.apoli.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.MiscUtil;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Pair;

public class ReflectDmgPower extends Power {

    private final ConditionFactory<Pair<Entity, Entity>>.Instance bientityCondition;
    private final ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition;
    private final Modifier damageModifier;
    private final RegistryKey<DamageType> damageType;
    private final boolean immune;
    private final ActionFactory<Pair<Entity, Entity>>.Instance bientityAction;

    private DamageSource damageSource;

    public ReflectDmgPower(PowerType<?> type, LivingEntity entity,
                      ConditionFactory<Pair<Entity, Entity>>.Instance bientityCondition,
                      ConditionFactory<Pair<DamageSource, Float>>.Instance damageCondition,
                      Modifier damageModifier,
                      RegistryKey<DamageType> damageType,
                      boolean immune,
                      ActionFactory<Pair<Entity, Entity>>.Instance bientityAction) {
        super(type, entity);
        this.bientityCondition = bientityCondition;
        this.damageCondition = damageCondition;
        this.damageModifier = damageModifier;
        this.damageType = damageType;
        this.immune = immune;
        this.bientityAction = bientityAction;
    }

    public void onDamage(DamageSource source, float amount) {
        Entity attacker = source.getAttacker();
        if (attacker == null) {
            return;
        }

        if (attacker instanceof LivingEntity) {
            float reflectedAmount = damageModifier == null ? amount : (float) damageModifier.apply(attacker, amount);
            DamageSource reflectedSource = getDamageSource(entity.getDamageSources(), entity);

            attacker.damage(reflectedSource, reflectedAmount);
        }

        bientityAction.accept(new Pair<>(entity, attacker));
    }

    private DamageSource getDamageSource(DamageSources damageSources, Entity attacker) {
        if (damageSource == null) {
            damageSource = MiscUtil.createDamageSource(damageSources, null, damageType, attacker);
        }
        return damageSource;
    }

    public boolean isValid(DamageSource source, float amount) {
        if (source.getSource() != null && source.getSource().getWorld() != null && source.getSource().getWorld().isClient()) return false;
        return
            (bientityCondition == null || source.getAttacker() == null || bientityCondition.test(new Pair<>(entity, source.getAttacker()))) &&
            (damageCondition == null || damageCondition.test(new Pair<>(source, amount)));
    }

    public boolean cancelsDamage() {
        return immune;
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
            OriginsContent.id("reflect_dmg"),
            new SerializableData()
                .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null)
                .add("damage_condition", ApoliDataTypes.DAMAGE_CONDITION, null)
                .add("damage_modifier", Modifier.DATA_TYPE, null)
                .add("damage_type", SerializableDataTypes.DAMAGE_TYPE, DamageTypes.PLAYER_ATTACK)
                .add("immune", SerializableDataTypes.BOOLEAN, true)
                .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION, null),
            data -> (type, entity) -> new ReflectDmgPower(
                type, entity,
                data.get("bientity_condition"),
                data.get("damage_condition"),
                data.get("damage_modifier"),
                data.get("damage_type"),
                data.getBoolean("immune"),
                data.get("bientity_action")
            )
        ).allowCondition();
    }
}