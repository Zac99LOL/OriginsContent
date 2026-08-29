package dev.zac99lol.originscontent.mixin.client.override_hotbar_power;

import dev.zac99lol.originscontent.client.SelectedSlotSyncClient;
import dev.zac99lol.originscontent.power.OverrideHotbarPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin {
    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getMainHandStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack originscontent$overrideMainHand(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity player)) return entity.getMainHandStack();

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
        return override != null ? override : player.getMainHandStack();
    }

    @Redirect(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getOffHandStack()Lnet/minecraft/item/ItemStack;"))
    private ItemStack originscontent$overrideOffHand(LivingEntity entity) {
        if (!(entity instanceof PlayerEntity player)) return entity.getOffHandStack();

        OverrideHotbarPower power = PowerHolderComponent.getPowers(player, OverrideHotbarPower.class)
            .stream().findFirst().orElse(null);
        if (power == null) {
            return player.getOffHandStack();
        }

        ItemStack override = power.getOffhandOverride();
        return override != null ? override : player.getOffHandStack();
    }
}
