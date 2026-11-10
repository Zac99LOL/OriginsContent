package dev.zac99lol.originscontent.mixin.breach_player_attack;

import com.pvpranked.enchantments.BreachEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BreachEnchantment.class)
public abstract class BreachEnchantmentMixin {
    @Inject(method = "modifyArmorEffectiveness", at = @At("HEAD"), cancellable = true)
    private void originscontent$checkForDamageType(ServerWorld world, int level, ItemStack stack, Entity user, DamageSource damageSource, MutableFloat armorEffectiveness, CallbackInfo ci) {
        if (!damageSource.isOf(DamageTypes.PLAYER_ATTACK)) ci.cancel();
    }
}
