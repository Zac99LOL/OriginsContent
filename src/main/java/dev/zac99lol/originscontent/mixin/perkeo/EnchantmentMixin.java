package dev.zac99lol.originscontent.mixin.perkeo;

import dev.zac99lol.originscontent.item.EmotionalSupportPerkeoItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.KnockbackEnchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void originscontent$knockbackAllowsPerkeo(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof KnockbackEnchantment && stack.getItem() instanceof EmotionalSupportPerkeoItem) {
            cir.setReturnValue(true);
        }
    }
}
