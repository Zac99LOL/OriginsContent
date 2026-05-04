// dev/zac99lol/originscontent/block/DormantCatalystBlock.java
package dev.zac99lol.originscontent.block;

import dev.zac99lol.originscontent.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DormantCatalystBlock extends Block {

    public DormantCatalystBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // Spawn sculk particles when mined
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                ParticleTypes.SCULK_SOUL,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                20,   // count
                0.3, 0.3, 0.3, // spread
                0.05  // speed
            );
            serverWorld.spawnParticles(
                ParticleTypes.SCULK_CHARGE_POP,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                10,
                0.2, 0.2, 0.2,
                0.02
            );
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public ItemStack getPickStack(net.minecraft.world.BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ModBlocks.DORMANT_CATALYST);
    }
}