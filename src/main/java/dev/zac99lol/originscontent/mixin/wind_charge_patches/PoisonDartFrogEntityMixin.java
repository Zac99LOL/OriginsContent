package dev.zac99lol.originscontent.mixin.wind_charge_patches;

import net.minecraft.entity.damage.DamageSource;
import org.ladysnake.pickyourpoison.common.entity.PoisonDartFrogEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(PoisonDartFrogEntity.class)
public abstract class PoisonDartFrogEntityMixin {
    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true, name = "amount")
    private float originscontent$noWindChargeDamage(float original, DamageSource source) {
        if (source.getType().msgId().equals("mace_smash")) {
            return 0F;
        }
        return original;
    }
}
