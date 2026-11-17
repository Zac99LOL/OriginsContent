package dev.zac99lol.originscontent.mixin.client.marshmallow_bullshit;

import dev.zac99lol.originscontent.init.ModCardinalComponents;
import dev.zac99lol.originscontent.item.MarshmallowOnAStickItem;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    @Inject(method = "getArmPose", at = @At("HEAD"), cancellable = true)
    private static void originscontent$getArmPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        var stackInHand = player.getStackInHand(hand);
        var component = ModCardinalComponents.HOLDING.get(player);
        if (stackInHand.getItem() instanceof MarshmallowOnAStickItem && component.isHolding()) {
            cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
        }
    }
}
