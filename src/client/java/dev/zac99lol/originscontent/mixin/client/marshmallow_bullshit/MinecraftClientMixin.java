package dev.zac99lol.originscontent.mixin.client.marshmallow_bullshit;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.zac99lol.originscontent.init.ModPackets;
import dev.zac99lol.originscontent.item.MarshmallowOnAStickItem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow
    @Final
    public GameOptions options;
    @Shadow
    @Nullable
    public ClientPlayerEntity player;
    @Unique
    private boolean originscontent$holding = false;

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;handleBlockBreaking(Z)V"))
    private void originscontent$holding(MinecraftClient instance, boolean breaking, Operation<Void> original) {
        var holding = this.options.attackKey.isPressed();
        if (holding != this.originscontent$holding) {
            this.originscontent$holding = holding;
            var buf = PacketByteBufs.create();
            buf.writeBoolean(holding);
            ClientPlayNetworking.send(ModPackets.HOLDING, buf);
        }
        if (this.player == null || !(this.player.getMainHandStack().getItem() instanceof MarshmallowOnAStickItem)) {
            original.call(instance, breaking);
        }
    }
}
