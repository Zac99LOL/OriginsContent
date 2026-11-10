package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.hud.minimap.module.MinimapRenderer;
import xaero.hud.minimap.module.MinimapSession;
import xaero.hud.render.module.ModuleRenderContext;

@Mixin(MinimapRenderer.class)
public abstract class MinimapRendererMixin {
    @Inject(method = "render(Lxaero/hud/minimap/module/MinimapSession;Lxaero/hud/render/module/ModuleRenderContext;Lnet/minecraft/client/gui/DrawContext;F)V", at = @At("HEAD"), cancellable = true)
    private void originscontent$cancel(MinimapSession session, ModuleRenderContext c, DrawContext guiGraphics, float partialTicks, CallbackInfo ci) {
        if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
            ci.cancel();
        }
    }
}
