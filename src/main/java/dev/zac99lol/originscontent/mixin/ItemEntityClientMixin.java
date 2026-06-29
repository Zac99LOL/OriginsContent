package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.item.EmotionalSupportPerkeoItem;
import net.minecraft.entity.ItemEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityClientMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        World world = self.getWorld();

        if (!world.isClient) return;
        if (!(self.getStack().getItem() instanceof EmotionalSupportPerkeoItem)) return;

        if (self.age % 5 == 0) {
            world.addParticle(
                ParticleTypes.END_ROD,
                self.getX() + (world.random.nextDouble() - 0.5) * 0.4,
                self.getY() + world.random.nextDouble() * 0.5,
                self.getZ() + (world.random.nextDouble() - 0.5) * 0.4,
                0.0, 0.02, 0.0
            );
        }
    }
}
