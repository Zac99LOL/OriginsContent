package dev.zac99lol.originscontent.mixin.marshmallow_stick;

import dev.zac99lol.originscontent.init.ModCardinalComponents;
import dev.zac99lol.originscontent.item.MarshmallowOnAStickItem;
import dev.zac99lol.originscontent.util.PlayerAttackHeld;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerAttackHeld {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean originscontent$isHoldingAttack() {
        return ModCardinalComponents.HOLDING.get(this).isHolding();
    }

    @Override
    public void originscontent$setHoldingAttack(boolean attackHeld) {
        ModCardinalComponents.HOLDING.get(this).setHolding(attackHeld);
    }

    @Inject(method = "resetLastAttackedTicks", at = @At("HEAD"), cancellable = true)
    private void originscontent$resetMarshmallowAttack(CallbackInfo ci) {
        if (this.getMainHandStack().getItem() instanceof MarshmallowOnAStickItem) {
            ci.cancel();
        }
    }
}
