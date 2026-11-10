package dev.zac99lol.originscontent.mixin.client.fix_knockback_hits;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {
    @Unique
    private boolean originscontent$sprint;

    @Inject(method = "sendMovementPackets", at = @At("HEAD"))
    private void originscontent$sprint(CallbackInfo ci) {
        ClientPlayerEntity self = (ClientPlayerEntity) (Object) this;
        boolean sprinting = self.isSprinting();
        if (sprinting != originscontent$sprint) {
            self.networkHandler.sendPacket(new ClientCommandC2SPacket(self,
                sprinting ? ClientCommandC2SPacket.Mode.START_SPRINTING
                          : ClientCommandC2SPacket.Mode.STOP_SPRINTING));
            originscontent$sprint = sprinting;
        }
    }
}
