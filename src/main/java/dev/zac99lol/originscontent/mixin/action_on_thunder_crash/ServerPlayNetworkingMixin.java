package dev.zac99lol.originscontent.mixin.action_on_thunder_crash;

import dev.zac99lol.originscontent.power.ActionOnThunderPower;
import doctor4t.defile.Defile;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworking.class)
public abstract class ServerPlayNetworkingMixin {
    @Inject(method = "send(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/util/Identifier;Lnet/minecraft/network/PacketByteBuf;)V", at = @At("HEAD"))
    private static void originscontent$triggerActionsOnThunderCrash(ServerPlayerEntity player, Identifier channelName, PacketByteBuf buf, CallbackInfo ci) {
        if (channelName == Defile.CLIENTBOUND_THUNDER_PACKET) {
            PowerHolderComponent.withPower(player, ActionOnThunderPower.class, p -> true, ActionOnThunderPower::activate);
        }
    }
}
