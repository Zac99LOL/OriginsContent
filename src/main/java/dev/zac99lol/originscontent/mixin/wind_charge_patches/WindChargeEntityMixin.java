package dev.zac99lol.originscontent.mixin.wind_charge_patches;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.pvpranked.entity.WindChargeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WindChargeEntity.class)
public abstract class WindChargeEntityMixin {
    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getRotationVector()Lnet/minecraft/util/math/Vec3d;", ordinal = 0))
    private Vec3d originscontent$arrowMomentumFix(Entity entity, @Local(name = "source", argsOnly = true) DamageSource source) {
        if (entity instanceof PlayerEntity && source.getType().msgId().equals("arrow")) {
            return entity.getRotationVector(0, 0);
        }
        return entity.getRotationVector();
    }

    @ModifyReturnValue(method = "damage", at = @At("RETURN"))
    private boolean originscontent$noDamage(boolean original) {
        return false;
    }
}
