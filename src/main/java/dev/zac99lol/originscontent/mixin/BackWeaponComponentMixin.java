package dev.zac99lol.originscontent.mixin;

import dev.doctor4t.arsenal.cca.BackWeaponComponent;
import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BackWeaponComponent.class)
public abstract class BackWeaponComponentMixin {

    @Unique
    private static final TagKey<Item> BACK_SLOT_ALLOWED = TagKey.of(
        RegistryKeys.ITEM,
        OriginsContent.id("back_slot_allowed")
    );
    
    @Inject(method = "setBackWeapon(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void originscontent$restrictBackWeapon(ItemStack backWeapon, CallbackInfoReturnable<Boolean> cir) {
        if (!backWeapon.isEmpty() && !backWeapon.isIn(BACK_SLOT_ALLOWED)) {
            cir.setReturnValue(false);
        }
    }
}