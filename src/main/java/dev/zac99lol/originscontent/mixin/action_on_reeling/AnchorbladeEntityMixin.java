package dev.zac99lol.originscontent.mixin.action_on_reeling;

import dev.doctor4t.arsenal.entity.AnchorbladeEntity;
import dev.zac99lol.originscontent.apoli.power.ActionOnReelingPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.CooldownPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnchorbladeEntity.class)
public abstract class AnchorbladeEntityMixin extends PersistentProjectileEntity {
    protected AnchorbladeEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void originscontent$trigger(CallbackInfo ci) {
        PowerHolderComponent.withPowers(getOwner(), ActionOnReelingPower.class, CooldownPower::canUse, ActionOnReelingPower::activate);
    }

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;multiply(D)Lnet/minecraft/util/math/Vec3d;", ordinal = 1))
    private void originscontent$trigger(EntityHitResult entityHitResult, CallbackInfo ci) {
        PowerHolderComponent.withPowers(getOwner(), ActionOnReelingPower.class, ActionOnReelingPower::onEntityHit, ActionOnReelingPower::activate);
    }
}
