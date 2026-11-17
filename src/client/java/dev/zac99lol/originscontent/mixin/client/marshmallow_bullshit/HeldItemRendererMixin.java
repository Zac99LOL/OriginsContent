package dev.zac99lol.originscontent.mixin.client.marshmallow_bullshit;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.zac99lol.originscontent.item.MarshmallowOnAStickItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public abstract class HeldItemRendererMixin {
    @Unique
    private int roastTicks;

    @Inject(method = "updateHeldItems", at = @At("HEAD"))
    private void originscontent$roasting(CallbackInfo ci) {
        var player = MinecraftClient.getInstance().player;
        if (player == null) return;
        var stack = player.getMainHandStack();
        if (!(stack.getItem() instanceof MarshmallowOnAStickItem)) return;
        if (player.originscontent$isHoldingAttack()) {
            this.roastTicks++;
        } else {
            roastTicks = 0;
        }
    }

    @WrapOperation(method = "renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private void originscontent$marshmallowProgress(HeldItemRenderer instance, AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
        var marsh = item.getItem() instanceof MarshmallowOnAStickItem;
        var holding = player.originscontent$isHoldingAttack();
        var both = marsh && holding;
        original.call(instance, player, tickDelta, pitch, hand, both ? this.originscontent$swingRoasting(tickDelta) : swingProgress, item, marsh ? holding ? this.originscontent$equipRoasting(tickDelta) : 0.0f : equipProgress, matrices, vertexConsumers, light);
    }

    @Unique
    private float originscontent$swingRoasting(float tickDelta) {
        var delta = this.doubleDelta(MathHelper.clamp(this.roastTicks / 6f, 0f, 1f));
        var prevDelta = this.doubleDelta(MathHelper.clamp((this.roastTicks - 1) / 6f, 0f, 1f));
        var current = 1.0f - delta * 0.06f;
        var prev = 1.0f - prevDelta * 0.06f;
        return 1.0f - (prev + tickDelta * (current - prev));
    }

    @Unique
    private float originscontent$equipRoasting(float tickDelta) {
        var delta3 = this.doubleDelta(MathHelper.clamp(this.roastTicks / 3f, 0f, 1f));
        var delta6 = this.doubleDelta(MathHelper.clamp(this.roastTicks / 6f, 0f, 1f));
        var prevDelta3 = this.doubleDelta(MathHelper.clamp((this.roastTicks - 1) / 3f, 0f, 1f));
        var prevDelta6 = this.doubleDelta(MathHelper.clamp((this.roastTicks - 1) / 6f, 0f, 1f));
        var current = delta3 * .1f + delta6 * 0.2f;
        var prev = prevDelta3 * .1f + prevDelta6 * 0.2f;
        return 0.5f - (prev + tickDelta * (current - prev));
    }

    @Unique
    private float doubleDelta(float delta) {
        return delta * delta * delta * delta;
    }
}
