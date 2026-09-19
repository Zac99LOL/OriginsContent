package dev.zac99lol.originscontent.mixin.client.cancel_inventory_actions_power;

import dev.zac99lol.originscontent.power.CancelInventoryActionsPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = MinecraftClient.class, priority = Integer.MAX_VALUE)
public class MinecraftClientMixin {
    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Redirect(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z", ordinal = 2))
    private boolean originscontent$blockHotbarKeySelect(KeyBinding instance) {
        boolean blocked = PowerHolderComponent.getPowers(player, CancelInventoryActionsPower.class)
            .stream().anyMatch(CancelInventoryActionsPower::blocksHotbarSlot);
        return blocked || instance.isPressed();
    }
}
