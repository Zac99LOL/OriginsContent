package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.power.CancelInventoryActionsPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

    @Final
    @Shadow public net.minecraft.entity.player.PlayerEntity player;

    @Inject(method = "scrollInHotbar", at = @At("HEAD"), cancellable = true)
    private void originscontent$blockHotbarScroll(double scrollAmount, CallbackInfo ci) {
        PowerHolderComponent.getPowers(player, CancelInventoryActionsPower.class)
            .stream().filter(CancelInventoryActionsPower::blocksHotbarSlot)
            .findFirst().ifPresent(power -> ci.cancel());
    }
}
