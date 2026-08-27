package dev.zac99lol.originscontent.mixin.client.cancel_inventory_actions_power;

import dev.zac99lol.originscontent.power.CancelInventoryActionsPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerInventory;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Redirect(method = "handleInputEvents", at = @At(
        value = "FIELD",
        target = "Lnet/minecraft/entity/player/PlayerInventory;selectedSlot:I",
        opcode = Opcodes.PUTFIELD
    ))
    private void originscontent$blockHotbarKeySelect(PlayerInventory inventory, int slot) {
        boolean blocked = PowerHolderComponent.getPowers(inventory.player, CancelInventoryActionsPower.class)
            .stream().anyMatch(CancelInventoryActionsPower::blocksHotbarSlot);
        if (!blocked) inventory.selectedSlot = slot;
    }
}
