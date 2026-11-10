package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xaero.map.gui.GuiMap;
import xaero.map.gui.ScreenBase;

@Mixin(GuiMap.class)
public abstract class GuiMapMixin extends ScreenBase {
    protected GuiMapMixin(Screen parent, Screen escape, Text titleIn) {
        super(parent, escape, titleIn);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void originscontent$close(CallbackInfo ci) {
        if (MinecraftClient.getInstance().world != null && MinecraftClient.getInstance().world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY)) {
            close();
        }
    }
}
