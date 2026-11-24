package dev.zac99lol.originscontent.mixin.divine_dominance;

import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderStage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldBorder.class)
public abstract class WorldBorderMixin {
    @Unique
    private final boolean originscontent$isDivineDominance = false;

    @Inject(method = "getStage", at = @At("HEAD"), cancellable = true)
    private void originscontent$kablammo(CallbackInfoReturnable<WorldBorderStage> cir) {
        if (originscontent$isDivineDominance) {
            cir.setReturnValue(WorldBorderStage.ORIGINSCONTENT_DIVINE_DOMINANCE_SHRINKING);
        }
    }
}
