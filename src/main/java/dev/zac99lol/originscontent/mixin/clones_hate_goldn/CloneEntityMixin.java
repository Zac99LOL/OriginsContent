package dev.zac99lol.originscontent.mixin.clones_hate_goldn;

import dev.overgrown.sync.factory.action.entity.summons.entities.clone.CloneEntity;
import dev.overgrown.sync.factory.action.entity.summons.utils.ExtraTameable;
import dev.overgrown.sync.factory.action.entity.summons.utils.Temporary;
import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.entity.ai.BullyPlayerGoal;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(CloneEntity.class)
public abstract class CloneEntityMixin extends HostileEntity implements ExtraTameable, CrossbowUser, Temporary {
    protected CloneEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void originscontent$IHATEGOLDNHITZ(CallbackInfo ci) {
        targetSelector.add(-1, new BullyPlayerGoal(this, 1, OriginsContent.UUIDs.GoldnHitz));
    }

    @Inject(method = "canTarget", at = @At("HEAD"), cancellable = true)
    private void originscontent$ifGoldnNearbyOnlyTargetGoldn(LivingEntity target, CallbackInfoReturnable<Boolean> cir) {
        Optional<PlayerEntity> maybeGoldn = this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(32), player -> player.getGameProfile().getId().equals(OriginsContent.UUIDs.GoldnHitz)).stream().findFirst();
        if (maybeGoldn.isPresent()) {
            if (!(target instanceof PlayerEntity player) || !player.getGameProfile().getId().equals(OriginsContent.UUIDs.GoldnHitz)) {
                cir.setReturnValue(false);
                maybeGoldn.ifPresent(this::setTarget);
            }
        }
    }
}
