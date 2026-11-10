package dev.zac99lol.originscontent.mixin.client.combat_log_display;

import dev.zac99lol.originscontent.client.OriginsContentClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method = "hasExperienceBar", at = @At("HEAD"), cancellable = true)
    private void originscontent$combatBarInCreative(CallbackInfoReturnable<Boolean> cir) {
        if (OriginsContentClient.getOutOfCombatTime() > System.currentTimeMillis()) cir.setReturnValue(true);
    }
}
