package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import dev.zac99lol.neverend.index.ModDimension;
import io.github.apace100.origins.screen.OriginDisplayScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = OriginDisplayScreen.class, priority = 1500)
public abstract class OriginDisplayScreenMixin extends Screen {
    @Shadow
    @Final
    protected static int windowHeight;

    protected OriginDisplayScreenMixin(Text title) {
        super(title);
    }

    @SuppressWarnings({"InvalidMemberReference", "MixinAnnotationTarget", "UnresolvedMixinReference"})
    @TargetHandler(mixin = "luna.lunali.mixin.client.origin.OriginDisplayScreenMixin", name = "renderOriginContent")
    @Inject(method = "@MixinSquared:Handler", at = @At(value = "INVOKE", target = "Lio/github/apace100/origins/origin/Origin;getPowerTypes()Ljava/lang/Iterable;"))
    private void originscontent$prependFake(DrawContext context, int mouseX, int mouseY, CallbackInfo ci, @Local(name = "textWidth") int textWidth, @Local(name = "x") int x, @Local(name = "y") LocalIntRef y) {
        if (shouldDisable()) {
            int startY = y.get();
            int endY = y.get() - 72 + windowHeight;
            OrderedText name = Language.getInstance().reorder(textRenderer.trimToWidth(Text.translatable("gui.originscontent.neverend_origin.title").formatted(Formatting.UNDERLINE, Formatting.AQUA), textWidth));
            Text desc = Text.translatable("gui.originscontent.neverend_origin.description");
            List<OrderedText> drawLines = textRenderer.wrapLines(desc, textWidth);
            if (y.get() >= startY - 24 && y.get() <= endY + 12)
                context.drawText(textRenderer, name, x, y.get(), 0xFFFFFF, false);
            for (OrderedText line : drawLines) {
                y.set(y.get() + 12);
                if (y.get() >= startY - 24 && y.get() <= endY + 12) {
                    context.drawText(textRenderer, line, x + 2, y.get(), 0xCCCCCC, false);
                }
            }

            y.set(y.get() + 14);
        }
    }

    @Redirect(method = "renderOriginContent", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;"))
    private MutableText originscontent$strikethrough(MutableText instance, Formatting formatting) {
        return shouldDisable() ? instance.formatted(Formatting.STRIKETHROUGH, Formatting.DARK_GRAY) : instance.formatted(formatting);
    }

    @Unique
    private boolean shouldDisable() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return false;
        return client.world.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY);
    }
}
