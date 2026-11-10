package dev.zac99lol.originscontent.mixin.combat_log;

import dev.zac99lol.originscontent.util.CanCombatLog;
import net.minecraft.server.ServerNetworkIo;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerNetworkIo.class)
public abstract class ServerNetworkIoMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void originscontent$tickConnections(CallbackInfo ci) {
        CanCombatLog.DISCONNECTED_PLAYERS.forEach(ServerPlayerEntity::playerTick);
    }
}
