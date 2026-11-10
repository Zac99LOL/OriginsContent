package dev.zac99lol.originscontent.mixin.shorter_black_rain;

import doctor4t.defile.cca.WorldEclipseAnimationComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WorldEclipseAnimationComponent.class)
public abstract class WorldEclipseAnimationComponentMixin {
    @ModifyArg(method = "serverTick", at = @At(value = "INVOKE", target = "Ldoctor4t/defile/cca/WorldBlackRainComponent;setTicks(I)V"))
    private int originscontent$slightlyShorter(int ticks) { // haha slightlyshorter get it
        return 1200;
    }
}
