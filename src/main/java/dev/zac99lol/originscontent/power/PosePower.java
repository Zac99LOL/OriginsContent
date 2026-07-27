package dev.zac99lol.originscontent.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.Prioritized;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class PosePower extends Power implements Prioritized<PosePower> {
    public static final SerializableDataType<EntityPose> ENTITY_POSE =
        SerializableDataType.enumValue(EntityPose.class);

    @Nullable private final EntityPose entityPose;
    @Nullable private final String armPoseName;
    private final int priority;

    public PosePower(PowerType<?> type, LivingEntity entity,
                     @Nullable EntityPose entityPose,
                     @Nullable String armPoseName,
                     int priority) {
        super(type, entity);
        this.entityPose = entityPose;
        this.armPoseName = armPoseName;
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Nullable
    public EntityPose getEntityPose() {
        return entityPose;
    }


    @Nullable
    public String getArmPoseName() {
        return armPoseName;
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<>(
            OriginsContent.id("pose"),
            new SerializableData()
                .add("entity_pose", ENTITY_POSE, null)
                .add("arm_pose", SerializableDataTypes.STRING, null)
                .add("priority", SerializableDataTypes.INT, 0),
            data -> (type, entity) -> new PosePower(type, entity,
                data.get("entity_pose"),
                data.isPresent("arm_pose") ? data.getString("arm_pose") : null,
                data.get("priority"))
        ).allowCondition();
    }
}
