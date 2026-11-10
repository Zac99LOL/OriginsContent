package dev.zac99lol.originscontent.mixin.combat_log;

import dev.zac99lol.originscontent.util.CanCombatLog;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    @Shadow
    public abstract void onDisconnected(Text reason);

    @Inject(method = "disconnect", at = @At("TAIL"))
    private void originscontent$disconnect(Text reason, CallbackInfo ci) {
        if (((CanCombatLog) this.player).originscontent$isFake()) {
            this.onDisconnected(reason);
        }
    }
}
