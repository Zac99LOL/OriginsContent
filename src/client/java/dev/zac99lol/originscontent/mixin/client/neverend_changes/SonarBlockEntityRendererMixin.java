package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.client.render.entity.block.SonarBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SonarBlockEntityRenderer.class)
public abstract class SonarBlockEntityRendererMixin {
    @ModifyVariable(method = "render*", at = @At("STORE"), name = "rotDegX")
    private float originscontent$noRotateX(float rotDegX) {
        return 0;
    }
}
