package dev.zac99lol.originscontent.mixin.client.max_health_update_faster_please;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    private long lastHealthCheckTime;

    @Inject(method = "renderStatusBars", at = @At("HEAD"))
    private void originscontent$forceInstantHealthUpdate(DrawContext context, CallbackInfo ci) {
        this.lastHealthCheckTime = Long.MAX_VALUE;
    }
}
