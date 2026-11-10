package dev.zac99lol.originscontent.mixin.lust_for_gold;

import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.NameTagItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NameTagItem.class)
public abstract class NameTagItemMixin {
    @Inject(method = "useOnEntity", at = @At("HEAD"), cancellable = true)
    private void originscontent$goldnIsNameable(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (entity instanceof PlayerEntity player && player.getGameProfile().getId().equals(OriginsContent.UUIDs.GoldnHitz)) {
            if (!user.getWorld().isClient && entity.isAlive()) {
                entity.setCustomName(stack.getName());
                stack.decrement(1);
            }

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
