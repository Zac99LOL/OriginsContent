package dev.zac99lol.originscontent.mixin.client.silly_mode;

import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LanguageOptionsScreen.class)
public abstract class LanguageOptionsScreenMixin {
    @Unique
    private int originscontent$buttonClicks = 0;

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/SimpleOption;createWidget(Lnet/minecraft/client/option/GameOptions;III)Lnet/minecraft/client/gui/widget/ClickableWidget;"))
    private ClickableWidget originscontent$addClickListener(SimpleOption<Boolean> instance, GameOptions options, int x, int y, int width) {
        return instance.createWidget(options, x, y, width, value -> {
            originscontent$buttonClicks++;
            if (originscontent$buttonClicks == 1000) {
                OriginsContent.SILLY_MODE = true;
                MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.PERIODIC_NOTIFICATION, Text.literal("congralation"), Text.literal("you ahve enaebled silly mode!1!!11!")));
            }
        });
    }
}
