package dev.zac99lol.originscontent.mixin.cancel_inventory_actions_power;

import dev.zac99lol.originscontent.power.CancelInventoryActionsPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Inject(method = "onPlayerCollision", at = @At("HEAD"), cancellable = true)
    private void originscontent$blockPickup(PlayerEntity player, CallbackInfo ci) {
        PowerHolderComponent.getPowers(player, CancelInventoryActionsPower.class)
            .stream().filter(CancelInventoryActionsPower::blocksPickup)
            .findFirst().ifPresent(power -> ci.cancel());
    }
}
