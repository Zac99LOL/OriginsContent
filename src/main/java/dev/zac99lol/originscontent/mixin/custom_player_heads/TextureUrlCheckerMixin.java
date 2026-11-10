package dev.zac99lol.originscontent.mixin.custom_player_heads;

import com.mojang.authlib.yggdrasil.TextureUrlChecker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(TextureUrlChecker.class)
public abstract class TextureUrlCheckerMixin {
    @Shadow
    @Final
    private static List<String> ALLOWED_DOMAINS;

    @Inject(method = "isDomainOnList", at = @At("HEAD"), cancellable = true)
    private static void originscontent$emulateDomainOnAllowedDomains(String domain, List<String> list, CallbackInfoReturnable<Boolean> cir) {
        if (list == ALLOWED_DOMAINS && domain.endsWith("zac99lol.dev")) cir.setReturnValue(true);
    }
}
