package dev.zac99lol.originscontent.mixin.void_crafting;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.init.ModItems;
import dev.zac99lol.originscontent.item.EmotionalSupportPerkeoItem;
import dev.zac99lol.originscontent.util.ItemEntityBullshit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.ladysnake.pickyourpoison.common.PickYourPoison;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity implements ItemEntityBullshit {
    public ItemEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract ItemStack getStack();

    @Unique
    private double originY = Double.NaN;

    @Unique
    private boolean returningToOrigin = false;

    @Override
    public boolean originscontent$returningToOrigin() {
        return returningToOrigin;
    }

    @Override
    public void originscontent$setReturning() {
        returningToOrigin = true;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void captureOriginAndReturn(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (self.getStack().getItem() instanceof EmotionalSupportPerkeoItem) self.setNoGravity(true);

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

    @Override
    public void tickInVoid() {
        ItemEntity self = (ItemEntity) (Object) this;

             if (OriginsContent.isPlayerHeadOf(OriginsContent.UUIDs.Zac99LOL, this.getStack())) summonItem(self, ModItems.EMOTIONAL_SUPPORT_PERKEO);
        else if (OriginsContent.isPlayerHeadOf(OriginsContent.UUIDs.Astrulux, this.getStack())) summonItem(self, PickYourPoison.LUXALAMANDER_BOWL);
        else if (OriginsContent.isPlayerHeadOf(OriginsContent.UUIDs.Olified , this.getStack())) summonItem(self, ModItems.OLLIE_TSUNDERE);
        else if (OriginsContent.isPlayerHeadOf(OriginsContent.UUIDs.BucketX2, this.getStack())) { summonItem(self, this.getStack()); this.getStack().setCount(Integer.MAX_VALUE); }
    }

    @Unique
    private void summonItem(ItemEntity entity, ItemConvertible item) {
        ItemStack oldStack = entity.getStack();
        ItemStack stack = new ItemStack(item);
        stack.setCount(oldStack.getCount());
        summonItem(entity, stack);
    }

    @Unique
    private void summonItem(ItemEntity entity, ItemStack stack) {
        Vec3d pos = entity.getPos();
        World world = entity.getWorld();
        Entity owner = entity.getOwner();
        entity.discard();

        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack);

        itemEntity.setInvulnerable(true);
        itemEntity.setNeverDespawn();
        itemEntity.setPickupDelay(100);
        if (owner != null) { entity.setOwner(owner.getUuid()); }

        itemEntity.setNoGravity(true);
        ((ItemEntityBullshit) this).originscontent$setReturning();
    }
}