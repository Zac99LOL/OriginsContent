package dev.zac99lol.originscontent.mixin;

import com.mojang.datafixers.util.Pair;
import dev.doctor4t.arsenal.util.BackSlot;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class BackSlotMixin {

    @Inject(method = "getBackgroundSprite", at = @At("HEAD"), cancellable = true)
    private void originscontent$scytheOutline(CallbackInfoReturnable<Pair<Identifier, Identifier>> cir) {
        if (!((Object) this instanceof BackSlot)) {
            return;
        }
        cir.setReturnValue(Pair.of(
            PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
            Identifier.of("originscontent", "item/empty_back_slot")
        ));
    }
}