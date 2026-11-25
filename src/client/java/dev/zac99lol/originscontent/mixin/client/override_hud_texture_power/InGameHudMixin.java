package dev.zac99lol.originscontent.mixin.client.override_hud_texture_power;

import dev.zac99lol.originscontent.apoli.power.OverrideHudTexturePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    // crosshair
    @ModifyArgs(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 0))
    private void originscontent$overrideCrosshairTexture(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::crosshairActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.crosshair);
                args.set(3, 0);
                args.set(4, 0);
            });
    }

    // crosshair attack cooldown ready
    @ModifyArgs(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void originscontent$overrideCrosshairAttackCooldown1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::attackIndicatorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.attackIndicator);
                args.set(3, 68);
                args.set(4, 0);
            });
    }
    // crosshair attack cooldown empty
    @ModifyArgs(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 2))
    private void originscontent$overrideCrosshairAttackCooldown2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::attackIndicatorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.attackIndicator);
                args.set(3, 36);
                args.set(4, 0);
            });
    }
    // crosshair attack cooldown fill
    @ModifyArgs(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 3))
    private void originscontent$overrideCrosshairAttackCooldown3(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::attackIndicatorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.attackIndicator);
                args.set(3, 52);
                args.set(4, 0);
            });
    }
    // hotbar attack cooldown empty
    @ModifyArgs(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 4))
    private void originscontent$overrideHotbarAttackCooldown1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::attackIndicatorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.attackIndicator);
                args.set(3, 0);
                args.set(4, 0);
            });
    }
    // hotbar attack cooldown fill
    @ModifyArgs(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 5))
    private void originscontent$overrideHotbarAttackCooldown2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::attackIndicatorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.attackIndicator);
                args.set(3, 18);
                args.set(4, 0);
            });
    }

    // health
    @ModifyArgs(method = "drawHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private void originscontent$overrideHealthBar(Args args, DrawContext context, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::healthActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.health);
                args.set(3, type.getU(halfHeart, blinking) - 16);
                if (v == 0) args.set(4, 0);
                else args.set(4, 9);
            });
    }
    // mount health
    @ModifyArgs(method = "renderMountHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 0))
    private void originscontent$overrideMountHealth1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::healthActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.health);
                args.set(3, 0);
                args.set(4, 18);
            });
    }
    @ModifyArgs(method = "renderMountHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void originscontent$overrideMountHealth2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::healthActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.health);
                args.set(3, 9);
                args.set(4, 18);
            });
    }
    @ModifyArgs(method = "renderMountHealth", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 2))
    private void originscontent$overrideMountHealth3(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::healthActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.health);
                args.set(3, 18);
                args.set(4, 18);
            });
    }

    // armor
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 0))
    private void originscontent$overrideArmorBar1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::armorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.armor);
                args.set(3, 18);
                args.set(4, 0);
            });
    }
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void originscontent$overrideArmorBar2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::armorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.armor);
                args.set(3, 9);
                args.set(4, 0);
            });
    }
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 2))
    private void originscontent$overrideArmorBar3(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::armorActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.armor);
                args.set(3, 0);
                args.set(4, 0);
            });
    }

    // hunger
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 3))
    private void originscontent$overrideHungerBar1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::hungerActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hunger);
                args.set(3, 0);
                if ((int) args.get(3) != 133) args.set(4, 0);
                else args.set(4, 9);
            });
    }
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 4))
    private void originscontent$overrideHungerBar2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::hungerActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hunger);
                args.set(3, 18);
                if ((int) args.get(3) < 88) args.set(4, 0);
                else args.set(4, 9);
            });
    }
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 5))
    private void originscontent$overrideHungerBar3(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::hungerActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hunger);
                args.set(3, 27);
                if ((int) args.get(3) < 88) args.set(4, 0);
                else args.set(4, 9);
            });
    }

    // air
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 6))
    private void originscontent$overrideAirBar1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::airActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.air);
                args.set(3, 0);
                args.set(4, 0);
            });
    }
    @ModifyArgs(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 7))
    private void originscontent$overrideAirBar2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::airActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.air);
                args.set(3, 9);
                args.set(4, 0);
            });
    }

    // experience
    @ModifyArgs(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 0))
    private void originscontent$overrideExperienceBar1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::experienceActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.experience);
                args.set(3, 0);
                args.set(4, 0);
            });
    }
    @ModifyArgs(method = "renderExperienceBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void originscontent$overrideExperienceBar2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::experienceActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.experience);
                args.set(3, 0);
                args.set(4, 5);
            });
    }

    // hotbar
    @ModifyArgs(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 0))
    private void originscontent$overrideHotbar1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::hotbarActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hotbar);
                args.set(3, 0);
                args.set(4, 0);
            });
    }
    @ModifyArgs(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void originscontent$overrideHotbar2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::hotbarActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hotbar);
                args.set(3, 0);
                args.set(4, 22);
            });
    }
    @ModifyArgs(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 2))
    private void originscontent$overrideHotbar3(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::hotbarActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hotbar);
                args.set(3, 24);
                args.set(4, 22);
            });
    }
    @ModifyArgs(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 3))
    private void originscontent$overrideHotbar4(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::hotbarActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hotbar);
                args.set(3, 53);
                args.set(4, 22);
            });
    }

    // mount jump bar
    @ModifyArgs(method = "renderMountJumpBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 0))
    private void originscontent$overrideJumpBar1(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::mountJumpBarActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hotbar);
                args.set(3, 0);
                args.set(4, 5);
            });
    }
    @ModifyArgs(method = "renderMountJumpBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 1))
    private void originscontent$overrideJumpBar2(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::mountJumpBarActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hotbar);
                args.set(3, 0);
                args.set(4, 0);
            });
    }
    @ModifyArgs(method = "renderMountJumpBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V", ordinal = 2))
    private void originscontent$overrideJumpBar3(Args args) {
        PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::mountJumpBarActive)
            .findFirst().ifPresent(power -> {
                args.set(0, power.hotbar);
                args.set(3, 0);
                args.set(4, 10);
            });
    }

    // carved pumpkin overlay
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal = 0))
    private Identifier originscontent$overridePumpkinOverlay(Identifier original) {
        return PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::carvedPumpkinOverlayActive)
            .map(power -> power.carvedPumpkinOverlay)
            .findFirst().orElse(original);
    }

    // spyglass overlay
    @ModifyArg(method = "renderSpyglassOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIFFIIII)V"))
    private Identifier originscontent$overrideSpyglassOverlay(Identifier original) {
        return PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::spyglassOverlayActive)
            .map(power -> power.spyglassOverlay)
            .findFirst().orElse(original);
    }

    // powder snow outline
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal = 1))
    private Identifier originscontent$overridePowderSnowOutline(Identifier original) {
        return PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::powderSnowOutlineActive)
            .map(power -> power.powderSnowOutline)
            .findFirst().orElse(original);
    }
}
