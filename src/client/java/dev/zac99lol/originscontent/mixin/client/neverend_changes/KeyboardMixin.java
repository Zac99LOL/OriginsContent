package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = "processF3", at = @At("HEAD"), cancellable = true)
    private void originscontent$cancel(int key, CallbackInfoReturnable<Boolean> cir) {
        if (client.world == null || !client.world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) return;
        if (key == 81) {
            ChatHud chatHud = this.client.inGameHud.getChatHud();
            chatHud.addMessage(Text.translatable("gui.originscontent.disabled_in_neverend"));
            cir.setReturnValue(true);
            return;
        }
        if (key != 72 && key != 80 && key != 68) {
            cir.setReturnValue(false);
        }
    }
}
