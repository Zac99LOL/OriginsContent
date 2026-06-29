package dev.zac99lol.originscontent.mixin;

import dev.zac99lol.originscontent.ModItems;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow
    public abstract ItemStack getStack();

    @Unique
    private double originY = Double.NaN;

    @Unique
    private boolean returningToOrigin = false;

    @Inject(method = "tick", at = @At("HEAD"))
    private void captureOriginAndReturn(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        self.setNoGravity(true);

        // Lazily capture origin on the first tick - by now this entity
        // (the real one server-side, the "ghost" one client-side) has
        // its actual position assigned, unlike at construction time.
        if (Double.isNaN(this.originY)) {
            this.originY = self.getY();
        }

        if (!this.returningToOrigin) return;

        double diff = this.originY - self.getY();

        // Close enough - snap into place and stop adjusting.
        if (Math.abs(diff) < 0.05) {
            self.setVelocity(self.getVelocity().x, 0.0, self.getVelocity().z);
            self.setPos(self.getX(), this.originY, self.getZ());
            this.returningToOrigin = false;
            return;
        }

        // Proportional speed: faster when far, slower as it approaches, capped.
        double speed = MathHelper.clamp(diff * 0.05, -0.4, 0.4);
        self.setVelocity(self.getVelocity().x, speed, self.getVelocity().z);
    }

    @Inject(method = "tick", at = @At(value = "RETURN"))
    private void checkIfVoid(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;

        if (self.getY() < self.getWorld().getDimension().minY() - 10) {
            if (!this.getStack().isOf(Items.NETHERITE_BLOCK)) { return; }
            summonPerkeo(self);
        }
    }

    @Unique
    private void summonPerkeo(ItemEntity entity) {
        ItemStack stack = new ItemStack(ModItems.EMOTIONAL_SUPPORT_PERKEO_ITEM);

        entity.setStack(stack);
        entity.setInvulnerable(true);
        entity.setNeverDespawn();
        entity.setPickupDelay(100);
        if (entity.getOwner() != null) { entity.setOwner(entity.getOwner().getUuid()); }

        entity.setNoGravity(true);
        this.returningToOrigin = true;
    }
}