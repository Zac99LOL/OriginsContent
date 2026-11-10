package dev.zac99lol.originscontent.mixin.shield_stunning_damage_type;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;isIn(Lnet/minecraft/registry/tag/TagKey;)Z", ordinal = 1))
    private boolean originscontent$alsoCheckForDamageType(DamageSource damageSource, TagKey<DamageType> damageTag) {
        return damageSource.isIn(damageTag) && damageSource.isOf(DamageTypes.PLAYER_ATTACK);
    }
}
