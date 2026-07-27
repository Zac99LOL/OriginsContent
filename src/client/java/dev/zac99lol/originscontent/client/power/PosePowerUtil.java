package dev.zac99lol.originscontent.client.power;

import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class PosePowerUtil {

    @Nullable
    @Environment(EnvType.CLIENT)
    public static BipedEntityModel.ArmPose getArmPose(dev.zac99lol.originscontent.power.PosePower power) {
        String armPoseName = power.getArmPoseName();
        if (armPoseName == null) return null;
        try {
            return BipedEntityModel.ArmPose.valueOf(armPoseName.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            OriginsContent.LOGGER.warn("Unknown arm_pose value: '{}'. Falling back to null.", armPoseName);
            return null;
        }
    }
}
