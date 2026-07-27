package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.power.CancelInventoryActionsPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public abstract class ScreenHandlerMixin {
    @Inject(method = "onSlotClick", at = @At("HEAD"), cancellable = true)
    private void originscontent$blockGuiMove(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        PowerHolderComponent.getPowers(player, CancelInventoryActionsPower.class)
            .stream().filter(CancelInventoryActionsPower::blocksGuiMove)
            .findFirst().ifPresent(power -> ci.cancel());
    }
}
