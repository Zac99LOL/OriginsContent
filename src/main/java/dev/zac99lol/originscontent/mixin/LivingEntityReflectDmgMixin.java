package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.power.ReflectDmgPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityReflectDmgMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void originscontent$reflect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity self = (LivingEntity) (Object) this;

        ReflectDmgPower power = PowerHolderComponent.getPowers(self, ReflectDmgPower.class).stream().filter(p -> p.matchesDamageCondition(source, amount)).findFirst().orElse(null);

        if (power == null) return;

        Entity attacker = source.getAttacker();

        if (attacker instanceof LivingEntity && power.matchesReflectCondition(source, amount)) {
            float reflectedAmount = power.applyReflectModifier(amount);
            Identifier damageTypeId = power.getReflectDamageType();
            DamageSource reflectSource = damageTypeId != null
                ? self.getDamageSources().create(
                    RegistryKey.of(RegistryKeys.DAMAGE_TYPE, damageTypeId),
                self, self)
                : self.getDamageSources().mobAttack(self);

            attacker.damage(reflectSource, reflectedAmount);
        }

        power.onSuccessfulParry(attacker);

        if (power.isImmune()) {
            cir.setReturnValue(false);
        }
    }
}
