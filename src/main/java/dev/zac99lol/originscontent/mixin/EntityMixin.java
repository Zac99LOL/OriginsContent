package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.power.PreventSprintingParticlesPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "spawnSprintingParticles", at = @At("HEAD"), cancellable = true)
    private void originscontent$preventSprintingParticles(CallbackInfo ci) {
        PowerHolderComponent.getPowers((Entity) (Object) this, PreventSprintingParticlesPower.class)
            .stream().findFirst().ifPresent(power -> ci.cancel());
    }
}
