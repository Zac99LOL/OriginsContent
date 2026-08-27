package dev.zac99lol.originscontent.mixin.cancel_inventory_actions_power;

import dev.zac99lol.originscontent.power.DisableOffhandPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class OffhandSlotMixin {

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void originscontent$blockOffhandGuiInsert(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Slot self = (Slot) (Object) this;
        if (!(self.inventory instanceof PlayerInventory inv)) {
            return;
        }
        if (self.getIndex() != 40) {
            return;
        }
        if (PowerHolderComponent.hasPower(inv.player, DisableOffhandPower.class)) {
            cir.setReturnValue(false);
        }
    }
}
