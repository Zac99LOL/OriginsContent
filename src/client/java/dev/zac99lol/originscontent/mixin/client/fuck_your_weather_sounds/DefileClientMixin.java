package dev.zac99lol.originscontent.mixin.client.fuck_your_weather_sounds;

import doctor4t.defile.DefileClient;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DefileClient.class)
public abstract class DefileClientMixin {
    @ModifyArg(method = "lambda$onInitializeClient$12", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions;getSoundVolume(Lnet/minecraft/sound/SoundCategory;)F"))
    private static SoundCategory originscontent$masterNotWeather(SoundCategory original) {
        return SoundCategory.MASTER;
    }
}
