package dev.zac99lol.originscontent.apoli;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.ClassUtil;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class ConditionalChoiceAction {

    public record Entry<T, U>(ActionFactory<T>.Instance action,
                              @Nullable ConditionFactory<U>.Instance condition,
                              int weight) {}

    public static <T, U> void action(SerializableData.Instance data, T t, Function<T, U> toConditionType) {
        List<Entry<T, U>> entries = data.get("actions");
        U u = toConditionType.apply(t);

        // 1. filter: all conditions are checked up front, before any action runs
        List<Entry<T, U>> candidates = new ArrayList<>(entries.size());
        int totalWeight = 0;
        for (Entry<T, U> entry : entries) {
            if (entry.weight() <= 0) continue;
            if (entry.condition() != null && !entry.condition().test(u)) continue;
            candidates.add(entry);
            totalWeight += entry.weight();
        }
        if (candidates.isEmpty()) return; // nothing eligible -> do nothing

        // 2. weighted pick among the survivors
        int roll = ThreadLocalRandom.current().nextInt(totalWeight);
        for (Entry<T, U> entry : candidates) {
            roll -= entry.weight();
            if (roll < 0) {
                entry.action().accept(t);
                return;
            }
        }
    }

    public static <T, U> ActionFactory<T> getFactory(
        SerializableDataType<ActionFactory<T>.Instance> actionDataType,
        SerializableDataType<ConditionFactory<U>.Instance> conditionDataType,
        Function<T, U> toConditionType) {
        return new ActionFactory<>(OriginsContent.id("conditional_choice"),
            new SerializableData().add("actions", SerializableDataType.list(SerializableDataType.compound(
                ClassUtil.castClass(Entry.class),
                new SerializableData()
                    .add("element", actionDataType)
                    .add("condition", conditionDataType, null)
                    .add("weight", SerializableDataTypes.INT, 1),
                inst -> new Entry<T, U>(inst.get("element"), inst.get("condition"), inst.getInt("weight")),
                (data, entry) -> {
                    SerializableData.Instance inst = data.new Instance();
                    inst.set("element", entry.action());
                    inst.set("condition", entry.condition());
                    inst.set("weight", entry.weight());
                    return inst;
                }))),
            (inst, t) -> action(inst, t, toConditionType));
    }

    // for action types whose condition takes the same input (entity, bientity)
    public static <T> ActionFactory<T> getFactory(
        SerializableDataType<ActionFactory<T>.Instance> actionDataType,
        SerializableDataType<ConditionFactory<T>.Instance> conditionDataType) {
        return getFactory(actionDataType, conditionDataType, t -> t);
    }
}