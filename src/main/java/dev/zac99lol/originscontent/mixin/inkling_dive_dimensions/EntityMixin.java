package dev.zac99lol.originscontent.mixin.inkling_dive_dimensions;

import doctor4t.defile.cca.DefileComponents;
import doctor4t.defile.cca.PlayerInklingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class, priority = 1)
public abstract class EntityMixin {
    @Unique
    private static final EntityDimensions DIVING_DIMENSIONS = EntityDimensions.changing(1.0F, 0.15F);

    @Shadow
    public abstract EntityPose getPose();
    @Shadow
    private EntityDimensions dimensions;
    @Shadow
    public abstract EntityDimensions getDimensions(EntityPose pose);
    @Shadow
    private float standingEyeHeight;
    @Shadow
    protected abstract float getEyeHeight(EntityPose pose, EntityDimensions dimensions);
    @Shadow
    protected abstract void refreshPosition();

    @Inject(method = "calculateDimensions", at = @At("HEAD"), cancellable = true)
    private void originscontent$overrideDimensionsWhileDiving(CallbackInfo ci) {
        if (((Object) this) instanceof PlayerEntity player) {
            PlayerInklingComponent component = (PlayerInklingComponent) DefileComponents.INKLING.get(player);
            if (component.isInkling()) {
                EntityPose entityPose = this.getPose();
                EntityDimensions entityDimensions2 = this.getDimensions(entityPose);
                this.dimensions = component.isDiving() ? DIVING_DIMENSIONS : entityDimensions2;
                this.standingEyeHeight = this.getEyeHeight(entityPose, entityDimensions2);
                this.refreshPosition();
                ci.cancel();
            }
        }
    }
}
