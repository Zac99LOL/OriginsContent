package dev.zac99lol.originscontent.mixin.combat_log;

import com.mojang.authlib.GameProfile;
import dev.zac99lol.originscontent.util.CanCombatLog;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Shadow
    public abstract List<ServerPlayerEntity> getPlayerList();

    @Shadow
    @Final
    private MinecraftServer server;

    @Inject(method = "createPlayer", at = @At("HEAD"))
    private void originscontent$onLogin(GameProfile profile, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        var matchingPlayers = getPlayerList().stream().filter(player -> player.getUuid().equals(profile.getId())).toList();

        for (ServerPlayerEntity player : matchingPlayers) {
            ((CanCombatLog) player).originscontent$setAllowDisconnect(true);

            this.server.getPlayerManager().remove(player);

            CanCombatLog.DISCONNECTED_PLAYERS.remove(player);
        }
    }
}
