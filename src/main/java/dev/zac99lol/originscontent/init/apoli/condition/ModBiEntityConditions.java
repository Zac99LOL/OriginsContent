package dev.zac99lol.originscontent.init.apoli.condition;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface ModBiEntityConditions {
    Map<ConditionFactory<Pair<Entity, Entity>>, Identifier> BIENTITY_CONDITIONS = new LinkedHashMap<>();

    ConditionFactory<Pair<Entity, Entity>> IS_NEAREST = createCondition("is_nearest",
        new ConditionFactory<>(OriginsContent.id("is_nearest"), new SerializableData()
            .add("distance", SerializableDataTypes.DOUBLE, 64D)
            .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null),
            (data, pair) -> {
                final Entity uno = pair.getLeft();
                final Entity dos = pair.getRight();
                final double distance = data.getDouble("distance");
                final Predicate<Pair<Entity, Entity>> bientityCondition = data.get("bientity_condition");

                List<Entity> others = uno.getWorld().getOtherEntities(uno, uno.getBoundingBox().expand(distance));
                Entity nearest = null;

                for (Entity other : others) {
                    if ((bientityCondition == null || bientityCondition.test(new Pair<>(uno, other))) && (nearest == null || uno.distanceTo(other) < uno.distanceTo(nearest))) nearest = other;
                }

                return dos.equals(nearest);
            }
        ));

    private static ConditionFactory<Pair<Entity, Entity>> createCondition(String id, ConditionFactory<Pair<Entity, Entity>> conditionFactory) {
        BIENTITY_CONDITIONS.put(conditionFactory, OriginsContent.id(id));
        return conditionFactory;
    }

    static void initialize() {
        BIENTITY_CONDITIONS.keySet().forEach(condition -> Registry.register(ApoliRegistries.BIENTITY_CONDITION, BIENTITY_CONDITIONS.get(condition), condition));
    }
}
