package dev.zac99lol.originscontent.mixin.neverend_advancements;

import dev.zac99lol.neverend.block.LockerBlock;
import dev.zac99lol.originscontent.init.ModAdvancements;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LockerBlock.class)
public abstract class LockerBlockMixin {
    @Inject(method = "onUse", at = @At(value = "INVOKE", target = "Ldev/zac99lol/neverend/cca/PlayerDivingSuitComponent;setHasDivingSuit(Z)V", ordinal = 0))
    private void originscontent$grant(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (!(world instanceof ServerWorld serverWorld)) return;

        Advancement advancement = serverWorld.getServer().getAdvancementLoader().get(ModAdvancements.DIVING_SUIT);
        ((ServerPlayerEntity) player).getAdvancementTracker().grantCriterion(advancement, "nohappen");
    }
}
