package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.power.DisableOffhandPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Blocks the swap-hands keybind's item swap while the player has
 * originscontent:disable_offhand, by cancelling the whole packet handler
 * before vanilla's non-atomic swap (read offhand -> write offhand ->
 * write mainhand) runs. This must happen here rather than on
 * PlayerInventory#setStack, since that swap isn't atomic: cancelling a
 * single write mid-swap loses the item instead of blocking the swap
 * (vanilla still performs the second write regardless of whether the
 * first one succeeded).
 * <p>
 * Only the SWAP_ITEM_WITH_OFFHAND action is affected here - every other
 * action (drop item, block breaking, etc) passes through untouched, and
 * the packet itself still reaches the server normally, so Origins
 * abilities bound to the same key still trigger.
 */
@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onPlayerAction", at = @At("HEAD"), cancellable = true)
    private void originscontent$blockOffhandSwap(PlayerActionC2SPacket packet, CallbackInfo ci) {
        if (packet.getAction() == PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND
            && PowerHolderComponent.hasPower(player, DisableOffhandPower.class)) {
            ci.cancel();
        }
    }
}
