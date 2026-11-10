package dev.zac99lol.originscontent.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.List;
import java.util.Map;

public class PanoramaBlock extends MultifaceGrowthBlock {
    protected static final Direction[] DIRECTIONS;
    private static final VoxelShape UP_SHAPE;
    private static final VoxelShape DOWN_SHAPE;
    private static final VoxelShape EAST_SHAPE;
    private static final VoxelShape WEST_SHAPE;
    private static final VoxelShape SOUTH_SHAPE;
    private static final VoxelShape NORTH_SHAPE;
    private static final Map<Direction, VoxelShape> SHAPES_FOR_DIRECTIONS;
    private final ImmutableMap<BlockState, VoxelShape> SHAPES;

    public PanoramaBlock(Settings settings) {
        super(settings);
        this.SHAPES = this.getShapesForStates(PanoramaBlock::getShapeForState);
    }

    private static VoxelShape getShapeForState(BlockState state) {
        VoxelShape voxelShape = VoxelShapes.empty();

        for (Direction direction : DIRECTIONS) {
            if (hasDirection(state, direction)) {
                voxelShape = VoxelShapes.union(voxelShape, SHAPES_FOR_DIRECTIONS.get(direction));
            }
        }

        return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.SHAPES.get(state);
    }

    @Override
    public LichenGrower getGrower() {
        return null;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        ItemStack stack = new ItemStack(this.asItem(), 0);
        for (Direction direction : DIRECTIONS) {
            if (hasDirection(state, direction)) {
                stack.increment(1);
            }
        }
        return List.of(stack);
    }

    static {
        DIRECTIONS = Direction.values();
        UP_SHAPE = Block.createCuboidShape(0, 15.9, 0, 16, 16, 16);
        DOWN_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 0.1, 16);
        EAST_SHAPE = Block.createCuboidShape(0, 0, 0, 0.1, 16, 16);
        WEST_SHAPE = Block.createCuboidShape(15.9, 0, 0, 16, 16, 16);
        SOUTH_SHAPE = Block.createCuboidShape(0, 0, 0, 16, 16, 0.1);
        NORTH_SHAPE = Block.createCuboidShape(0, 0, 15.9, 16, 16, 16);
        SHAPES_FOR_DIRECTIONS = Util.make(Maps.newEnumMap(Direction.class), (shapes) -> {
            shapes.put(Direction.NORTH, SOUTH_SHAPE);
            shapes.put(Direction.EAST, WEST_SHAPE);
            shapes.put(Direction.SOUTH, NORTH_SHAPE);
            shapes.put(Direction.WEST, EAST_SHAPE);
            shapes.put(Direction.UP, UP_SHAPE);
            shapes.put(Direction.DOWN, DOWN_SHAPE);
        });
    }
}
