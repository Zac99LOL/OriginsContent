package dev.zac99lol.originscontent.mixin.combat_log;

import dev.zac99lol.originscontent.util.CanCombatLog;
import io.netty.channel.Channel;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {
    @Shadow
    public abstract PacketListener getPacketListener();

    @Shadow
    private Channel channel;

    @Inject(method = "handleDisconnection", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/listener/PacketListener;onDisconnected(Lnet/minecraft/text/Text;)V"), cancellable = true)
    private void originscontent$handleDisconnection(CallbackInfo ci) {
        if (this.getPacketListener() instanceof ServerPlayNetworkHandler listener) {
            if (!((CanCombatLog) listener.getPlayer()).originscontent$canDisconnect()) {
                this.channel.close();
                ((CanCombatLog) listener.getPlayer()).originscontent$onRealDisconnect();
                ci.cancel();
            }
        }
    }
}
