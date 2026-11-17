package dev.zac99lol.originscontent.mixin.marshmallow_stick;

import dev.zac99lol.originscontent.init.ModItems;
import dev.zac99lol.originscontent.item.MarshmallowOnAStickItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "getEatSound", at = @At("HEAD"), cancellable = true)
    private void originscontent$burnt(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.getItem() == ModItems.MARSHMALLOW_ON_A_STICK) {
            cir.setReturnValue(((MarshmallowOnAStickItem) ModItems.MARSHMALLOW_ON_A_STICK).getEatSound((ItemStack) (Object) this));
        }
    }
}
