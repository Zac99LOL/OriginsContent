package dev.zac99lol.originscontent.mixin.clones_last_infinitely;

import dev.overgrown.sync.factory.action.entity.summons.entities.clone.CloneEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CloneEntity.class)
public abstract class CloneEntityMixin {
    @Shadow
    private int maxTicks;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void originscontent$overrideMaxTicks(EntityType<? extends CloneEntity> entityType, World world, CallbackInfo ci) {
        this.maxTicks = Integer.MAX_VALUE;
    }
}
