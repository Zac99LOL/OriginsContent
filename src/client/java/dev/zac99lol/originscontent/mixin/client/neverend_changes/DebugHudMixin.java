package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At("HEAD"))
    private void originscontent$turnOffAllDebugOptions(DrawContext context, CallbackInfo ci) {
        if (client.world != null && client.world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
            client.options.debugProfilerEnabled = false;
            client.options.debugTpsEnabled = false;
            client.getEntityRenderDispatcher().setRenderHitboxes(false);
            client.debugRenderer.showChunkBorder = false;
        }
    }
}
