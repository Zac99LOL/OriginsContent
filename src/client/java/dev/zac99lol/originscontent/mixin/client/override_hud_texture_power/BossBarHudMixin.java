package dev.zac99lol.originscontent.mixin.client.override_hud_texture_power;

import dev.zac99lol.originscontent.apoli.power.OverrideHudTexturePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BossBarHud.class)
public abstract class BossBarHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @ModifyArg(method = "renderBossBar(Lnet/minecraft/client/gui/DrawContext;IILnet/minecraft/entity/boss/BossBar;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    private Identifier originscontent$overrideBarsTexture(Identifier original) {
        return PowerHolderComponent.getPowers(client.player, OverrideHudTexturePower.class).stream().filter(OverrideHudTexturePower::bossBarActive)
            .map(power -> power.bossBar)
            .findFirst().orElse(original);
    }
}
