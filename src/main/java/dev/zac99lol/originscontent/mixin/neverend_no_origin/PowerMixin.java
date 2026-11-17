package dev.zac99lol.originscontent.mixin.neverend_no_origin;

import dev.zac99lol.neverend.index.ModDimension;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Power.class)
public abstract class PowerMixin {
    @Shadow
    protected LivingEntity entity;

    @Shadow
    protected PowerType<?> type;

    @Inject(method = "isActive", at = @At("RETURN"), cancellable = true)
    private void originscontent$suppress(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) return;
        if (entity == null || type == null) return;

        if (entity.getWorld().getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
            if (shouldSuppress(type)) {
                cir.setReturnValue(false);
            }
        }
    }

    @Unique
    private boolean shouldSuppress(PowerType<?> type) {
        return !type.getIdentifier().getNamespace().equals("background") && !type.getDescription().toString().endsWith("§r§r");
    }
}
