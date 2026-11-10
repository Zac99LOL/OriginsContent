package dev.zac99lol.originscontent.mixin.rideable_grandgousiers;

import dev.zac99lol.neverend.entity.GrandgousierEntity;
import dev.zac99lol.originscontent.init.ModItems;
import dev.zac99lol.originscontent.util.GrandgousierBullshit;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrandgousierEntity.class)
public abstract class GrandgousierEntityMixin extends WaterCreatureEntity implements Saddleable {
    protected GrandgousierEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique private static final float HEAD_FORWARD = 0.5F;
    @Unique private static final float SEAT_HEIGHT = 4.9F;
    @Unique private static final double SPEED_SCALE = 0.04F;

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void originscontent$initData(CallbackInfo ci) {
        this.dataTracker.startTracking(GrandgousierBullshit.SADDLED, false);
        this.dataTracker.startTracking(GrandgousierBullshit.AUTO_TARGETING, true);
    }

    // saddle
    @Override
    public boolean isSaddled() {
        return this.dataTracker.get(GrandgousierBullshit.SADDLED);
    }
    @Override
    public boolean canBeSaddled() {
        return this.isAlive() && !this.isSaddled();
    }
    @Override
    public void saddle(@Nullable SoundCategory sound) {
        this.dataTracker.set(GrandgousierBullshit.SADDLED, true);
        if (sound != null) this.playSound(SoundEvents.ENTITY_PIG_SADDLE, 0.5F, 1.0F);
    }

    // mounting
    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (this.isSaddled() && !this.hasPassengers() && !player.shouldCancelInteraction()) {
            if (!this.getWorld().isClient()) player.startRiding(this);
            return ActionResult.success(this.getWorld().isClient());
        }

        return super.interactMob(player, hand);
    }
    @Override
    public boolean shouldDismountUnderwater() {
        return false;
    }
    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return !this.hasPassengers();
    }
    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        Vec3d off = new Vec3d(0, 0, HEAD_FORWARD).rotateY(-this.headYaw * ((float) Math.PI / 180F));
        positionUpdater.accept(passenger, this.getX() + off.x, this.getY() + SEAT_HEIGHT + passenger.getHeightOffset(), this.getZ() + off.z);
    }

    // steering
    @Unique private boolean originscontent$hasSteerer() {
        return this.isSaddled() && this.getFirstPassenger() instanceof PlayerEntity p && p.isHolding(ModItems.POOLFISH_ON_A_STICK);
    }
    @Override public @Nullable LivingEntity getControllingPassenger() {
        return this.originscontent$hasSteerer() && !this.dataTracker.get(GrandgousierBullshit.AUTO_TARGETING)
            ? (PlayerEntity) this.getFirstPassenger() : null;
    }
    @Override protected void tickControlled(PlayerEntity controllingPlayer, Vec3d movementInput) {
        super.tickControlled(controllingPlayer, movementInput);
        this.setRotation(controllingPlayer.getYaw(), controllingPlayer.getPitch() * 0.6F);
        this.prevYaw = this.bodyYaw = this.headYaw = this.getYaw();
    }
    @Override protected Vec3d getControlledMovementInput(PlayerEntity controllingPlayer, Vec3d movementInput) {
        float forward = controllingPlayer.forwardSpeed;
        double y = 0.0;

        if (forward != 0.0F) {
            double pitchRad = Math.toRadians(controllingPlayer.getPitch());
            y = -Math.sin(pitchRad) * forward;
        }

        if (controllingPlayer.jumping) {
            y += 0.25D;
        }

        return new Vec3d(controllingPlayer.sidewaysSpeed, y, forward).multiply(1.75F);
    }
    @Override protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return (float) (this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * SPEED_SCALE);
    }

    /*@Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    private void originscontent$ridden(Vec3d input, CallbackInfo ci) {
        if (this.isAlive() && this.isTouchingWater() && this.getControllingPassenger() instanceof PlayerEntity) {
            Vec3d vel = this.getVelocity().multiply(0.9F);
            if (input.z != 0) vel = vel.add(this.getRotationVector().multiply(input.x, input.y, input.z * this.getMovementSpeed()));
            this.setVelocity(vel);
            this.move(MovementType.SELF, this.getVelocity());
            ci.cancel();
        }
    }*/

    @Inject(method = "tick", at = @At("TAIL"))
    private void originscontent$autoTarget(CallbackInfo ci) {
        if (this.getWorld().isClient() || this.age % 5 != 0) return;
        PlayerEntity nearest = null;
        if (this.originscontent$hasSteerer()) {
            double best = Double.MAX_VALUE;
            for (PlayerEntity p : this.getWorld().getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(5.0F),
                p -> p.isAlive() && !p.isSpectator() && !p.isCreative() && !this.hasPassenger(p))) {
                double d = this.squaredDistanceTo(p);
                if (d < best) { best = d; nearest = p; }
            }
        }
        boolean auto = nearest != null;
        if (this.dataTracker.get(GrandgousierBullshit.AUTO_TARGETING) != auto) this.dataTracker.set(GrandgousierBullshit.AUTO_TARGETING, auto);
        if (auto) this.setTarget(nearest);
        else if (this.originscontent$hasSteerer() && this.getTarget() != null) this.setTarget(null);
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void originscontent$filterTargets(LivingEntity target, CallbackInfo ci) {
        if (target == null) return;
        if (this.getPassengerList().contains(target)) { ci.cancel(); return; }
        if (this.originscontent$hasSteerer()
            && !(target instanceof PlayerEntity && this.getBoundingBox().expand(5.0F).intersects(target.getBoundingBox()))) {
            ci.cancel();
        }
    }

    // saddle persistence
    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void originscontent$saveSaddled(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("Saddled", this.dataTracker.get(GrandgousierBullshit.SADDLED));
    }
    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void originscontent$readSaddled(NbtCompound nbt, CallbackInfo ci) {
        this.dataTracker.set(GrandgousierBullshit.SADDLED, nbt.getBoolean("Saddled"));
    }
}
