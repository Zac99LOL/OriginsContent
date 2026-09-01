package dev.zac99lol.originscontent.mixin.client.disable_offhand_power;

import dev.zac99lol.originscontent.power.DisableOffhandPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Inject(method = "clickSlot", at = @At("HEAD"), cancellable = true)
    private void originscontent$pleaseStopUsingYourOffhandIDoNotConsent(int syncId, int slotId, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (button == 40 && PowerHolderComponent.hasPower(player, DisableOffhandPower.class)) ci.cancel();
    }
}
