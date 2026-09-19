package dev.zac99lol.originscontent.mixin.client.resource_based_item_bars;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.*;

@Mixin(DrawContext.class)
public class DrawContextMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemBarVisible()Z"))
    private boolean originscontent$isItemBarVisible(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return stack.isItemBarVisible();

        return nbt.contains("BarResource") || nbt.contains("BarStepOverride") || stack.isItemBarVisible();
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItemBarColor()I"))
    private int originscontent$overrideItemBarColor(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return stack.getItemBarColor();

        return nbt.contains("BarColor") ? originscontent$parseColor(nbt.getString("BarColor")) : stack.getItemBarColor();
    }

    @Redirect(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItemBarStep()I"))
    private int originscontent$overrideItemBarStep(ItemStack stack) {
        if (client.player == null) return 0;

        NbtCompound nbt = stack.getNbt();
        if (nbt == null) return stack.getItemBarStep();
        if (nbt.contains("BarStepOverride")) return nbt.getInt("BarStepOverride");
        if (!nbt.contains("BarResource")) return stack.getItemBarStep();

        Identifier resource = new Identifier(nbt.getString("BarResource"));
        PowerType<?> powerType = new PowerTypeReference<>(resource);
        Power power = PowerHolderComponent.KEY.get(client.player).getPower(powerType);
        if (power instanceof VariableIntPower p) {
            return Math.round((p.getValue() - p.getMin()) / (float)(p.getMax() - p.getMin()) * 13);
        } else if (power instanceof CooldownPower p) {
            return Math.round(p.getFill() * 13);
        } else {
            return 0;
        }
    }

    @Unique
    private int originscontent$parseColor(String hex) {
        if (hex.equalsIgnoreCase("rainbow")) {
            long time = client.world != null ? client.world.getTime() : 0;
            return originscontent$rainbowColor(time, 60);
        }
        try {
            return Integer.parseInt(hex.replace("#", ""), 16);
        } catch (NumberFormatException e) {
            return 0xFFFFFF;
        }
    }

    @Unique
    private int originscontent$rainbowColor(long time, int cycleDurationTicks) {
        float hue = (time % cycleDurationTicks) / (float) cycleDurationTicks;
        return Color.HSBtoRGB(hue, 1.0f, 1.0f);
    }
}
