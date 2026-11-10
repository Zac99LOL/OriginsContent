package dev.zac99lol.originscontent.mixin.reflect_dmg_power;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.apoli.power.ReflectDmgPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class, PlayerEntity.class})
public abstract class LivingEntityMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void originscontent$reflect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        ReflectDmgPower power = PowerHolderComponent.getPowers(self, ReflectDmgPower.class)
            .stream().filter(p -> p.isValid(source, amount))
            .findFirst().orElse(null);

        if (power == null) {
            OriginsContent.LOGGER.debug("Power not found");
            return;
        }

        power.onDamage(source, amount);

        if (power.cancelsDamage()) {
            cir.setReturnValue(false);
        }
    }
}
