package dev.zac99lol.originscontent.mixin.override_hotbar_power;

import dev.zac99lol.originscontent.power.OverrideHotbarPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "disablesShield", at = @At("HEAD"), cancellable = true)
    private void originscontent$stopShieldStunning(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        OverrideHotbarPower power = PowerHolderComponent.getPowers(entity, OverrideHotbarPower.class)
            .stream().findFirst().orElse(null);
        if (power != null) {
            int selectedSlot = entity instanceof PlayerEntity player ? player.getInventory().selectedSlot : 0;
            ItemStack override = power.getOverride(selectedSlot);
            if (override != null) cir.setReturnValue(override.getItem() instanceof AxeItem);
        }
    }
}
