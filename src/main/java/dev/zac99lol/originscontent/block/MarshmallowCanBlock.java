package dev.zac99lol.originscontent.block;

import dev.zac99lol.originscontent.block.entity.MarshmallowCanBlockEntity;
import dev.zac99lol.originscontent.init.ModItems;
import dev.zac99lol.originscontent.init.ModSoundEvents;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MarshmallowCanBlock extends BlockWithEntity {
    public static final IntProperty LEVEL = IntProperty.of("level", 0, 8);
    public static final VoxelShape SHAPE = Block.createCuboidShape(4, 0, 4, 12, 10, 12);

    public MarshmallowCanBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LEVEL, 8));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MarshmallowCanBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MarshmallowCanBlockEntity marshmallowCanBlockEntity) {
            if (player.getStackInHand(hand).isOf(Items.STICK)) {
                if (marshmallowCanBlockEntity.decrementMarshmallowCount()) {
                    if (player.getStackInHand(hand).getCount() == 1) {
                        player.setStackInHand(hand, new ItemStack(ModItems.MARSHMALLOW_ON_A_STICK));
                    } else {
                        player.getStackInHand(hand).decrement(1);
                        player.giveItemStack(new ItemStack(ModItems.MARSHMALLOW_ON_A_STICK));
                    }
                    player.playSound(ModSoundEvents.MARSHMALLOW_CAN_TAKE, .5f, (float) (1f + world.random.nextGaussian()) / 20f);
                    return ActionResult.SUCCESS;
                }
            } else if (player.getStackInHand(hand).isOf(ModItems.MARSHMALLOW)) {
                int i = marshmallowCanBlockEntity.incrementMarshmallowCount(player.getStackInHand(hand).getCount());
                if (i > 0) {
                    player.getStackInHand(hand).decrement(i);
                    player.playSound(ModSoundEvents.MARSHMALLOW_CAN_STORE, .5f, (float) (1f + world.random.nextGaussian()) / 20f);
                    return ActionResult.SUCCESS;
                }
            }
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(LEVEL, (int) Math.ceil((double) ctx.getStack().getOrCreateNbt().getInt("marshmallowCount") / 8));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MarshmallowCanBlockEntity marshmallowCanBlockEntity) {
                ItemStack itemStack = new ItemStack(state.getBlock().asItem());
                if (!world.isClient) {
                    itemStack.getOrCreateNbt().putInt("marshmallowCount", marshmallowCanBlockEntity.getMarshmallowCount());
                }
                ItemEntity itemEntity = new ItemEntity(world, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, itemStack);
                itemEntity.setToDefaultPickupDelay();
                world.spawnEntity(itemEntity);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        int marshmallowCount = itemStack.getOrCreateNbt().getInt("marshmallowCount");
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof MarshmallowCanBlockEntity marshmallowCanBlockEntity) {
            marshmallowCanBlockEntity.setMarshmallowCount(marshmallowCount);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        int marshmallowCount = stack.getOrCreateNbt().getInt("marshmallowCount");
        String plural = (marshmallowCount == 1 ? "" : "s");
        tooltip.add(Text.literal(marshmallowCount + " ").append(
            Text.translatable("itemTooltip.originscontent.marshmallow" + plural)
        ).setStyle(Style.EMPTY.withColor(1184810)));

        super.appendTooltip(stack, world, tooltip, options);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = Direction.DOWN;
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos)
            ? Blocks.AIR.getDefaultState()
            : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }
}
