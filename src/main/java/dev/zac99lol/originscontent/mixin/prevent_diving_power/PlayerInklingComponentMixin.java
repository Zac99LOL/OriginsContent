package dev.zac99lol.originscontent.mixin.prevent_diving_power;

import dev.zac99lol.originscontent.apoli.power.PreventDivingPower;
import doctor4t.defile.cca.PlayerInklingComponent;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInklingComponent.class)
public abstract class PlayerInklingComponentMixin {
    @Shadow
    @Final
    private PlayerEntity player;

    @Inject(method = "dive", at = @At("HEAD"), cancellable = true)
    private void originscontent$preventDivingAndTriggerAction(CallbackInfo ci) {
        if (PowerHolderComponent.hasPower(player, PreventDivingPower.class)) ci.cancel();
        PowerHolderComponent.getPowers(player, PreventDivingPower.class).forEach(PreventDivingPower::executeAction);
    }
}
