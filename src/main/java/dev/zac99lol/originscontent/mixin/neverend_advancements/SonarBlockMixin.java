package dev.zac99lol.originscontent.mixin.neverend_advancements;

import dev.zac99lol.neverend.block.SonarBlock;
import dev.zac99lol.originscontent.init.ModAdvancements;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(SonarBlock.class)
public abstract class SonarBlockMixin {
    @Inject(method = "neighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V", ordinal = 0))
    private void originscontent$grant(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify, CallbackInfo ci) {
        if (!world.isClient()) {
            Advancement advancement = Objects.requireNonNull(world.getServer()).getAdvancementLoader().get(ModAdvancements.SONAR);
            PlayerLookup.around((ServerWorld) world, pos.toCenterPos(), 8D)
                .forEach(player -> player.getAdvancementTracker().grantCriterion(advancement, "impossible"));
        }
    }
}
