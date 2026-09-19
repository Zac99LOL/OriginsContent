package dev.zac99lol.originscontent.mixin.no_black_rain_ink_placement;

import doctor4t.defile.Defile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Defile.class)
public abstract class DefileMixin {
    @Redirect(method = "lambda$onInitialize$5", at = @At(value = "INVOKE", target = "Ldoctor4t/defile/command/DefileCommand;getBlackRainIntensity()F"))
    private static float originscontent$ohNoYouDont() {
        return 0;
    }
}
