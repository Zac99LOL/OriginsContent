package dev.zac99lol.originscontent;

import dev.zac99lol.neverend.block.LiminalEndPortalFrameBlock;
import dev.zac99lol.neverend.index.ModBlocks;
import dev.zac99lol.originscontent.block.entity.MarshmallowCanBlockEntity;
import dev.zac99lol.originscontent.util.MarshmallowCanProvider;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndPortalFrameBlock;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.Direction;
import snownee.jade.api.*;

@WailaPlugin
public class OriginsContentJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if (accessor instanceof BlockAccessor blockAccessor) {
                if (blockAccessor.getBlock() == ModBlocks.LIMINAL_PORTAL) {
                    return registration.blockAccessor().from(blockAccessor)
                        .blockState(Blocks.END_PORTAL.getDefaultState()).build();
                }
                if (blockAccessor.getBlock() == ModBlocks.LIMINAL_END_PORTAL_FRAME && blockAccessor.getSide() != Direction.DOWN) {
                    return registration.blockAccessor().from(blockAccessor)
                        .blockState(Blocks.END_PORTAL_FRAME.getDefaultState()
                            .with(HorizontalFacingBlock.FACING, blockAccessor.getBlockState().get(HorizontalFacingBlock.FACING))
                            .with(EndPortalFrameBlock.EYE, blockAccessor.getBlockState().get(LiminalEndPortalFrameBlock.EYE)))
                        .build();
                }
            }
            return accessor;
        });

        registration.registerItemStorageClient(MarshmallowCanProvider.INSTANCE);
    }

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerItemStorage(MarshmallowCanProvider.INSTANCE, MarshmallowCanBlockEntity.class);
    }
}
