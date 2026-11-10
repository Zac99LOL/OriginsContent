package dev.zac99lol.originscontent.mixin.mace_enchantable_at_etable;

import com.pvpranked.enchantments.MaceEnchantment;
import com.pvpranked.enchantments.WindBurstEnchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WindBurstEnchantment.class)
public abstract class WindBurstEnchantmentMixin extends MaceEnchantment {
    protected WindBurstEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Inject(method = "getMinPower", at = @At("HEAD"), cancellable = true)
    private void originscontent$overrideMinPower(int level, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(level * 30);
    }
}
