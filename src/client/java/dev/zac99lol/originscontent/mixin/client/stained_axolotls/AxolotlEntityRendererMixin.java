package dev.zac99lol.originscontent.mixin.client.stained_axolotls;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.util.LiminalAxolotl;
import net.minecraft.client.render.entity.AxolotlEntityRenderer;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxolotlEntityRenderer.class)
public abstract class AxolotlEntityRendererMixin {
    @Unique
    private static final Identifier LIMINAL_TEXTURE = OriginsContent.id("textures/entity/axolotl_stained.png");

    @Inject(method = "getTexture(Lnet/minecraft/entity/passive/AxolotlEntity;)Lnet/minecraft/util/Identifier;", at = @At("HEAD"), cancellable = true)
    private void originscontent$overrideTexture(AxolotlEntity axolotl, CallbackInfoReturnable<Identifier> cir) {
        if (((LiminalAxolotl) axolotl).originscontent$isLiminal()) {
            cir.setReturnValue(LIMINAL_TEXTURE);
        }
    }
}
