package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.power.CancelTotemsPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents totem of undying activation for entities holding the
 * originscontent:cancel_totems power. Cancels at HEAD so the totem is
 * never consumed - it stays in the entity's inventory unused.
 */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void originscontent$cancelTotems(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (PowerHolderComponent.hasPower(self, CancelTotemsPower.class)) {
            cir.setReturnValue(false);
        }
    }
}
