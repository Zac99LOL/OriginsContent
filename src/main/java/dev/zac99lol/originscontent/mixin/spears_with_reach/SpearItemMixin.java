package dev.zac99lol.originscontent.mixin.spears_with_reach;

import com.google.common.collect.ImmutableMultimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.llamalad7.mixinextras.sugar.Local;
import com.notunanancyowen.spears.items.SpearItem;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(SpearItem.class)
public abstract class SpearItemMixin {
    @Inject(method = "<init>", at = @At(
        value = "INVOKE",
        target = "Lcom/google/common/collect/ImmutableMultimap$Builder;put(Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableMultimap$Builder;",
        ordinal = 1, shift = At.Shift.AFTER))
    private void originscontent$addReachAttribute(ToolMaterial material, int swingAnimationTicks, float chargeDamageMultiplier, float chargeDelaySeconds, float maxDurationForDismountSeconds, float minSpeedForDismount, float maxDurationForChargeKnockbackInSeconds, float minSpeedForChargeKnockback, float maxDurationForChargeDamageInSeconds, float minRelativeSpeedForChargeDamage, SoundEvent hitSound, SoundEvent attackSound, SoundEvent useSound, Item.Settings settings, CallbackInfo ci, @Local(name = "builder") ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder) {
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(UUID.fromString("e0a28d86-52f0-4faf-96f9-f7ce569fdf22"), "Tool modifier", 1.5F, EntityAttributeModifier.Operation.ADDITION));
    }
}
