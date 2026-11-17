package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.client.render.SkyboxRenderer;
import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyboxRenderer.class)
public abstract class SkyboxRendererMixin {

    @Inject(method = "setup", at = @At("HEAD"), cancellable = true)
    private void originscontent$cancelInLevel3(MatrixStack matrices, Matrix4f viewMatrix, Matrix4f positionMatrix, float tickDelta, ShaderProgram shader, BlockPos pos, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.gameRenderer != null && client.world != null) {
            if (client.world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
                Camera camera = client.gameRenderer.getCamera();

                if (pos.getY() > 267 && camera.getPos().getY() < 267) {
                    ci.cancel();
                }
            }
        }
    }
}
