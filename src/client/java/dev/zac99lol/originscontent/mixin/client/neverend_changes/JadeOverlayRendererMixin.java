package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import snownee.jade.overlay.OverlayRenderer;

@Mixin(OverlayRenderer.class)
public abstract class JadeOverlayRendererMixin {
    @Inject(method = "shouldShow", at = @At("HEAD"), cancellable = true)
    private static void originscontent$hide(CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
            cir.setReturnValue(false);
        }
    }
}
