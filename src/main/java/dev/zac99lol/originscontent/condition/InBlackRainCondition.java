package dev.zac99lol.originscontent.condition;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.api.DefileApi;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class InBlackRainCondition {
    public static boolean condition(Entity entity) {
        if (!(entity.getWorld() instanceof ServerWorld world)) {
            return false;
        }

        return DefileApi.isBlackRainActive(world) && world.isSkyVisible(entity.getBlockPos());
    }

    public static ConditionFactory<Entity> getFactory() {
        return new ConditionFactory<>(
            new Identifier(OriginsContent.MOD_ID, "in_black_rain"),
            new SerializableData(),
            (data, entity) -> condition(entity)
        );
    }
}
