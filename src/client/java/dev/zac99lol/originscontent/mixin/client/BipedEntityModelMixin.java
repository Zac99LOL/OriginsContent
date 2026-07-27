package dev.zac99lol.originscontent.mixin.client;

import dev.zac99lol.originscontent.client.power.PosePowerUtil;
import dev.zac99lol.originscontent.power.PosePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow
    public BipedEntityModel.ArmPose leftArmPose;

    @Shadow
    public BipedEntityModel.ArmPose rightArmPose;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void originscontent$applyArmPose(T entity, float limbAngle, float limbDistance,
                                             float animationProgress, float headYaw, float headPitch,
                                             CallbackInfo ci) {
        PowerHolderComponent.getPowers(entity, PosePower.class)
            .stream().max(Comparator.comparing(PosePower::getPriority))
            .ifPresent(power -> {
                BipedEntityModel.ArmPose armPose = PosePowerUtil.getArmPose(power);
                if (armPose != null) {
                    this.leftArmPose = armPose;
                    this.rightArmPose = armPose;
                }
            });
    }
}
