package dev.zac99lol.originscontent.mixin.cancel_inventory_actions_power;

import dev.zac99lol.originscontent.power.CancelInventoryActionsPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayerEntity player;

    @Inject(method = "onPlayerAction", at = @At("HEAD"), cancellable = true)
    private void originscontent$blockDroppingItems(PlayerActionC2SPacket packet, CallbackInfo ci) {
        PlayerActionC2SPacket.Action action = packet.getAction();

        if ((action == PlayerActionC2SPacket.Action.DROP_ITEM
            || action == PlayerActionC2SPacket.Action.DROP_ALL_ITEMS)
            && PowerHolderComponent.getPowers(player, CancelInventoryActionsPower.class)
            .stream().anyMatch(CancelInventoryActionsPower::blocksDrop)) {
            ci.cancel();
        }
    }

    @Inject(method = "onUpdateSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void originscontent$blockHotbarSlotChange(UpdateSelectedSlotC2SPacket packet, CallbackInfo ci) {
        PowerHolderComponent.getPowers(player, CancelInventoryActionsPower.class)
            .stream().filter(CancelInventoryActionsPower::blocksHotbarSlot)
            .findFirst().ifPresent(power -> ci.cancel());
    }
}
