package dev.zac99lol.originscontent.init.apoli.condition;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.api.DefileApi;
import dev.zac99lol.originscontent.util.CanCombatLog;
import doctor4t.defile.cca.DefileComponents;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModEntityConditions {
    Map<ConditionFactory<Entity>, Identifier> ENTITY_CONDITIONS = new LinkedHashMap<>();

    ConditionFactory<Entity> DEFILE_RAINING = createCondition("defile.is_raining",
        new ConditionFactory<>(OriginsContent.id("defile.is_raining"), new SerializableData(),
            (data, entity) -> DefileApi.isBlackRainActive(entity.getWorld())));

    ConditionFactory<Entity> DEFILE_DIVING = createCondition("defile.is_diving",
        new ConditionFactory<>(OriginsContent.id("defile.is_diving"), new SerializableData(),
            (data, entity) -> {
                if (!(entity instanceof PlayerEntity player)) return false;
                return DefileComponents.INKLING.get(player).isDiving();
            }));

    ConditionFactory<Entity> IN_COMBAT = createCondition("in_combat",
        new ConditionFactory<>(OriginsContent.id("in_combat"), new SerializableData(),
            (data, entity) -> {
                if (!(entity instanceof CanCombatLog player)) return false;
                return !player.originscontent$canDisconnect();
            }));

    private static ConditionFactory<Entity> createCondition(String id, ConditionFactory<Entity> conditionFactory) {
        ENTITY_CONDITIONS.put(conditionFactory, OriginsContent.id(id));
        return conditionFactory;
    }

    static void initialize() {
        ENTITY_CONDITIONS.keySet().forEach(condition -> Registry.register(ApoliRegistries.ENTITY_CONDITION, ENTITY_CONDITIONS.get(condition), condition));
    }
}
