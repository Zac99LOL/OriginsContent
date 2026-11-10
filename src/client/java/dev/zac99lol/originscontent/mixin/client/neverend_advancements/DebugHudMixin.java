package dev.zac99lol.originscontent.mixin.client.neverend_advancements;

import dev.zac99lol.originscontent.init.ModPackets;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "render", at = @At("HEAD"))
    private void originscontent$triggerAdvancement(DrawContext context, CallbackInfo ci) {
        if (this.client != null && this.client.getNetworkHandler() != null) {
            ClientPlayNetworking.send(ModPackets.OPEN_F3, PacketByteBufs.create());
        }
    }
}
