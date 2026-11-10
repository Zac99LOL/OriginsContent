package dev.zac99lol.originscontent.mixin.client.rideable_grandgousiers;

import dev.zac99lol.neverend.entity.GrandgousierEntity;
import dev.zac99lol.neverend.entity.DeepGrandgousierEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Shadow
    public abstract Entity getFocusedEntity();

    @ModifyVariable(method = "clipToSpace", at = @At("HEAD"), argsOnly = true)
    private double originscontent$letsBackUpALittle(double normal) {
        Entity focused = this.getFocusedEntity();

        if (focused != null && focused.hasVehicle()) {
            Entity vehicle = focused.getVehicle();

            if (vehicle instanceof DeepGrandgousierEntity) {
                return normal * 30;
            } else if (vehicle instanceof GrandgousierEntity) {
                return normal + 3;
            }
        }

        return normal;
    }
}
