package dev.zac99lol.originscontent.mixin.grandgousier_goals;

import dev.zac99lol.neverend.entity.GrandgousierEntity;
import dev.zac99lol.originscontent.mixin.MobEntityAccessor;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.passive.AxolotlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrandgousierEntity.class)
public abstract class GrandgousierEntityMixin {
    @Shadow
    private int feedingCooldown;

    @Inject(method = "initGoals", at = @At("TAIL"))
    private void originscontent$targetAxolotls(CallbackInfo ci) {
        ((MobEntityAccessor) this).getGoalSelector().add(4, new ActiveTargetGoal<>((GrandgousierEntity) (Object) this, AxolotlEntity.class, true, (predicate) -> this.feedingCooldown == 0));
    }
}
