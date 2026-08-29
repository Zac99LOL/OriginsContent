package dev.zac99lol.originscontent.mixin.harry_wants_cobwebs_to_last_longer;

import io.github.apace100.origins.content.TemporaryCobwebBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TemporaryCobwebBlock.class)
public abstract class TemporaryCobwebBlockMixin {
    @Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
    private void originscontent$temporaryCobwebsLastLonger(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving, CallbackInfo ci) {
        worldIn.scheduleBlockTick(pos, (TemporaryCobwebBlock) (Object) this, 600);
        ci.cancel();
    }
}
