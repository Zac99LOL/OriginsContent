package dev.zac99lol.originscontent.mixin;

import dev.doctor4t.arsenal.util.BackSlot;
import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin {

    @Unique
    private static final TagKey<Item> BACK_SLOT_ALLOWED = TagKey.of(
        RegistryKeys.ITEM,
        OriginsContent.id("back_slot_allowed")
    );

    @Inject(method = "canInsert", at = @At("HEAD"), cancellable = true)
    private void originscontent$restrictBackSlot(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof BackSlot) {
            cir.setReturnValue(stack.isIn(BACK_SLOT_ALLOWED));
        }
    }
}