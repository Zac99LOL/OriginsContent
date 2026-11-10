package dev.zac99lol.originscontent.mixin.client.combat_log_display;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.client.OriginsContentClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    private int scaledHeight;

    @Shadow
    private int scaledWidth;

    @Shadow
    public abstract TextRenderer getTextRenderer();

    @Unique
    private static final Identifier COMBAT_BAR_TEXTURE = OriginsContent.id("textures/gui/combat_bar.png");

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    private void originscontent$overrideExperienceBar(DrawContext context, int x, CallbackInfo ci) {
        if (OriginsContentClient.getOutOfCombatTime() > System.currentTimeMillis()) {
            ci.cancel();
            this.client.getProfiler().push("expBar");
            int width = (int)((OriginsContentClient.getOutOfCombatTime() - System.currentTimeMillis()) / (float)(OriginsContentClient.getCombatDuration() * 1000) * 183.0F);
            int y = this.scaledHeight - 32 + 3;
            context.drawTexture(COMBAT_BAR_TEXTURE, x, y, 0, 64, 182, 5);
            if (width > 0) {
                context.drawTexture(COMBAT_BAR_TEXTURE, x, y, 0, 69, width, 5);
            }

            this.client.getProfiler().pop();
            this.client.getProfiler().push("expLevel");
            String string = "" + (int) Math.ceil((OriginsContentClient.getOutOfCombatTime() - System.currentTimeMillis()) / 1000F);
            int k = (this.scaledWidth - this.getTextRenderer().getWidth(string)) / 2;
            int l = this.scaledHeight - 31 - 4;
            context.drawText(this.getTextRenderer(), string, k + 1, l, 0, false);
            context.drawText(this.getTextRenderer(), string, k - 1, l, 0, false);
            context.drawText(this.getTextRenderer(), string, k, l + 1, 0, false);
            context.drawText(this.getTextRenderer(), string, k, l - 1, 0, false);
            context.drawText(this.getTextRenderer(), string, k, l, 16719909, false);
            this.client.getProfiler().pop();
        }
    }
}
