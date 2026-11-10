package dev.zac99lol.originscontent.mixin.mace_enchantable_at_etable;

import com.pvpranked.enchantments.MaceEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaceEnchantment.class)
public abstract class MaceEnchantmentMixin extends Enchantment {
    protected MaceEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Inject(method = "isAvailableForRandomSelection", at = @At("HEAD"), cancellable = true)
    private void originscontent$yes(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
