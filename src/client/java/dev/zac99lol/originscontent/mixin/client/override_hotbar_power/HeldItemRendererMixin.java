package dev.zac99lol.originscontent.mixin.client.override_hotbar_power;

import dev.zac99lol.originscontent.client.SelectedSlotSyncClient;
import dev.zac99lol.originscontent.power.OverrideHotbarPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Shadow
    private ItemStack mainHand;

    @Shadow
    private ItemStack offHand;

    @Redirect(method = "updateHeldItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack originscontent$overrideMainHandStack(ClientPlayerEntity player) {
        OverrideHotbarPower power = PowerHolderComponent.getPowers(player, OverrideHotbarPower.class)
            .stream().findFirst().orElse(null);
        if (power == null) {
            return player.getMainHandStack();
        }

        int selectedSlot;
        if (player == MinecraftClient.getInstance().player) {
            selectedSlot = player.getInventory().selectedSlot;
        } else {
            selectedSlot = SelectedSlotSyncClient.get(player);
        }

        ItemStack override = power.getOverride(selectedSlot);
        this.mainHand = override != null ? override : player.getMainHandStack();
        return override != null ? override : player.getMainHandStack();
    }

    @Redirect(method = "updateHeldItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack originscontent$overrideOffHandStack(ClientPlayerEntity player) {
        OverrideHotbarPower power = PowerHolderComponent.getPowers(player, OverrideHotbarPower.class)
            .stream().findFirst().orElse(null);
        if (power == null) {
            return player.getOffHandStack();
        }

        ItemStack override = power.getOffhandOverride();
        this.offHand = override != null ? override : player.getOffHandStack();
        return override != null ? override : player.getOffHandStack();
    }
}
