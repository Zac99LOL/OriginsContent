package dev.zac99lol.originscontent.mixin.moldy_ink;

import doctor4t.defile.world.Inksplosion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inksplosion.class)
public abstract class InksplosionMixin {
    @Shadow
    @Final
    private World world;

    @Inject(method = "affectPos", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", shift = At.Shift.AFTER))
    private void originscontent$scheduleInkExpiry(BlockPos.Mutable blockPos, CallbackInfo ci) {
        world.scheduleBlockTick(blockPos, world.getBlockState(blockPos).getBlock(), 80, TickPriority.VERY_LOW);
    }
}
