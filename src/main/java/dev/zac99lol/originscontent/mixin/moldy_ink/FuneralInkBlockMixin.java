package dev.zac99lol.originscontent.mixin.moldy_ink;

import dev.zac99lol.originscontent.api.DefileApi;
import doctor4t.defile.block.FuneralInkBlock;
import doctor4t.defile.cca.DefileComponents;
import net.minecraft.block.BlockState;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.tick.TickPriority;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FuneralInkBlock.class)
public abstract class FuneralInkBlockMixin extends MultifaceGrowthBlock {
    public FuneralInkBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void originscontent$letMeLive(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        ci.cancel();
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (DefileApi.isBlackRainActive(world)) {
            world.scheduleBlockTick(pos, state.getBlock(), DefileComponents.BLACK_RAIN.get(world).getTicks() + world.random.nextInt(40), TickPriority.VERY_LOW);
        } else if (world.isPlayerInRange(pos.getX(), pos.getY(), pos.getZ(), 4)) {
            world.scheduleBlockTick(pos, state.getBlock(), 60 + world.random.nextInt(40), TickPriority.VERY_LOW);
        } else {
            world.breakBlock(pos, false);
        }
    }
}
