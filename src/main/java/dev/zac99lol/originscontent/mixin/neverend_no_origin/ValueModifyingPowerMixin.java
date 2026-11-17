package dev.zac99lol.originscontent.mixin.neverend_no_origin;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import io.github.apace100.apoli.util.modifier.Modifier;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ValueModifyingPower.class)
public abstract class ValueModifyingPowerMixin extends Power {
    public ValueModifyingPowerMixin(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    @Inject(method = "getModifiers", at = @At("HEAD"), cancellable = true)
    private void originscontent$clear(CallbackInfoReturnable<List<Modifier>> cir) {
        if (!isActive()) cir.setReturnValue(List.of());
    }
}
