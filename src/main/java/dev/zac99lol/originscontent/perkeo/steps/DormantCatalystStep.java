// dev/zac99lol/originscontent/perkeo/steps/DormantCatalystStep.java
package dev.zac99lol.originscontent.perkeo.steps;

import dev.zac99lol.originscontent.ModSounds;
import dev.zac99lol.originscontent.block.ModBlocks;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

public class DormantCatalystStep {

    private static final Map<BlockPos, Integer> trackedCatalysts = new HashMap<>();
    private static final int REQUIRED_TICKS = 120;
    private static final int MAX_Y = -32;

    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(DormantCatalystStep::onServerTick);

        // Track when a player places a sculk catalyst below y=-32
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient()) return ActionResult.PASS;

            ItemStack held = player.getStackInHand(hand);
            if (!held.isOf(Items.SCULK_CATALYST)) return ActionResult.PASS;

            BlockPos placed = hitResult.getBlockPos().offset(hitResult.getSide());

            ((ServerWorld) world).getServer().execute(() -> {
                if (world.getBlockState(placed).isOf(Blocks.SCULK_CATALYST)
                    && placed.getY() < MAX_Y) {
                    trackedCatalysts.put(placed, 0);
                }
            });

            return ActionResult.PASS;
        });

        // Stop tracking if the catalyst is broken
        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            if (state.isOf(Blocks.SCULK_CATALYST)) {
                trackedCatalysts.remove(pos);
            }
            return true;
        });
    }

    private static void onServerTick(MinecraftServer server) {
        for (ServerWorld world : server.getWorlds()) {
            if (!world.getRegistryKey().equals(ServerWorld.OVERWORLD)) continue;
            if (world.getTime() % 20 != 0) continue;

            trackedCatalysts.entrySet().removeIf(entry -> {
                BlockPos pos = entry.getKey();

                if (!world.getBlockState(pos).isOf(Blocks.SCULK_CATALYST)) return true;

                if (isFullyEnclosed(world, pos)) {
                    if (entry.getValue() == 0) { world.playSound(null, pos,
                        ModSounds.DORMANT_CATALYST_ACTIVATE,
                        SoundCategory.BLOCKS,
                        1.0f, 0.5f); }
                    int newTicks = entry.getValue() + 20;
                    entry.setValue(newTicks);

                    if (newTicks >= REQUIRED_TICKS) {
                        // Replace sculk catalyst with dormant catalyst block
                        world.setBlockState(pos, ModBlocks.DORMANT_CATALYST.getDefaultState());

                        // Play a deep sculk-like sound at the position
                        world.playSound(
                            null,
                            pos,
                            ModSounds.DORMANT_CATALYST_CONVERT,
                            SoundCategory.BLOCKS,
                            2.0f,  // volume
                            0.4f   // pitch (lower = deeper/more ominous)
                        );
                        return true;
                    }
                } else {
                    entry.setValue(0);
                }

                return false;
            });
        }
    }

    private static boolean isFullyEnclosed(ServerWorld world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos neighbour = pos.offset(dir);
            if (!world.getBlockState(neighbour).isOpaqueFullCube(world, neighbour)) {
                return false;
            }
        }
        return true;
    }
}