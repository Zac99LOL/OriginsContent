package dev.zac99lol.originscontent.mixin.client.override_hotbar_power;

import com.llamalad7.mixinextras.sugar.Local;
import dev.zac99lol.originscontent.power.OverrideHotbarPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Final
    @Shadow
    private MinecraftClient client;

    @ModifyArg(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHotbarItem(Lnet/minecraft/client/gui/DrawContext;IIFLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/item/ItemStack;I)V", ordinal = 0), index = 5)
    private ItemStack originscontent$overrideHotbar(ItemStack stack, @Local(ordinal = 4) int m) {
        PlayerEntity player = client.player;
        if (player == null) return stack;

        OverrideHotbarPower power = PowerHolderComponent.getPowers(player, OverrideHotbarPower.class)
            .stream().findFirst().orElse(null);
        if (power == null) {
            return stack;
        }

        ItemStack override = power.getOverride(m);
        return override != null ? override : stack;
    }

    @Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack originscontent$overrideOffhandStack(PlayerEntity player) {
        OverrideHotbarPower power = PowerHolderComponent.getPowers(player, OverrideHotbarPower.class)
            .stream().findFirst().orElse(null);
        if (power == null) {
            return player.getOffHandStack();
        }

        ItemStack override = power.getOffhandOverride();
        return override != null ? override : player.getOffHandStack();
    }

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack originscontent$overrideMainHandStack(PlayerInventory inventory) {
        OverrideHotbarPower power = PowerHolderComponent.getPowers(inventory.player, OverrideHotbarPower.class)
            .stream().findFirst().orElse(null);
        if (power == null) {
            return inventory.getMainHandStack();
        }

        ItemStack override = power.getOverride(inventory.selectedSlot);
        return override != null ? override : inventory.getMainHandStack();
    }
}
