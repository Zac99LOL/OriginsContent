package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.common.minimap.waypoints.Waypoint;
import xaero.hud.minimap.waypoint.render.world.WaypointWorldRenderContext;
import xaero.hud.minimap.waypoint.render.world.WaypointWorldRenderReader;

@Mixin(WaypointWorldRenderReader.class)
public abstract class WaypointWorldRenderReaderMixin {
    @Inject(method = "isHidden(Lxaero/common/minimap/waypoints/Waypoint;Lxaero/hud/minimap/waypoint/render/world/WaypointWorldRenderContext;)Z", at = @At("HEAD"), cancellable = true)
    private void originscontent$hide(Waypoint element, WaypointWorldRenderContext context, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
            cir.setReturnValue(true);
        }
    }
}
