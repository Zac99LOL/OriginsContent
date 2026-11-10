package dev.zac99lol.originscontent.mixin.stained_axolotls;

import dev.zac99lol.originscontent.init.ModSoundEvents;
import dev.zac99lol.originscontent.util.AxolotlData;
import dev.zac99lol.originscontent.util.LiminalAxolotl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AxolotlEntity.class)
public abstract class AxolotlEntityMixin extends AnimalEntity implements LiminalAxolotl {
    @Unique
    private int originscontent$submergedTicks = 0;

    protected AxolotlEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void originscontent$initData(CallbackInfo ci) {
        this.dataTracker.startTracking(AxolotlData.LIMINAL, false);
    }

    @Override
    public boolean originscontent$isLiminal() {
        return this.dataTracker.get(AxolotlData.LIMINAL);
    }

    @Override
    public void originscontent$setLiminal(boolean value) {
        this.dataTracker.set(AxolotlData.LIMINAL, value);
    }

    @Inject(method = "mobTick", at = @At("HEAD"))
    private void originscontent$checkWater(CallbackInfo ci) {
        if (!this.originscontent$isLiminal() && this.isSubmergedInWater()/* && this.getWorld().getRegistryKey() == ModDimension.POOLROOMS_REGISTRY_KEY*/) {
            if (originscontent$submergedTicks > 60) {
                this.playSound(ModSoundEvents.AXOLOTL_CONVERT, 1.0F, 1.0F);
                this.originscontent$setLiminal(true);
            } else {
                originscontent$submergedTicks++;
            }
        } else {
            originscontent$submergedTicks--;
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void originscontent$saveLiminal(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("Liminal", this.originscontent$isLiminal());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void originscontent$readLiminal(NbtCompound nbt, CallbackInfo ci) {
        this.originscontent$setLiminal(nbt.getBoolean("Liminal"));
    }

    @Inject(method = "copyDataToStack", at = @At("TAIL"))
    private void originscontent$saveToStack(ItemStack stack, CallbackInfo ci) {
        stack.getOrCreateNbt().putBoolean("Liminal", this.originscontent$isLiminal());
    }

    @Inject(method = "copyDataFromNbt", at = @At("TAIL"))
    private void originscontent$readFromStack(NbtCompound nbt, CallbackInfo ci) {
        this.originscontent$setLiminal(nbt.getBoolean("Liminal"));
    }
}
