package dev.zac99lol.originscontent.init;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.block.PanoramaBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBlocks {
    Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    Map<BlockItem, Identifier> BLOCK_ITEMS = new LinkedHashMap<>();
    Block PANORAMA = createBlock("panorama", new PanoramaBlock(AbstractBlock.Settings.create().breakInstantly().luminance(state -> 15).pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.GLASS).solid()), true);

    private static <T extends Block> T createBlock(String id, T block, boolean createItem) {
        BLOCKS.put(block, OriginsContent.id(id));
        if (createItem) BLOCK_ITEMS.put(new BlockItem(block, new FabricItemSettings()), OriginsContent.id(id));
        return block;
    }

    static void initialize() {
        BLOCKS.forEach((block, identifier) -> Registry.register(Registries.BLOCK, identifier, block));
        BLOCK_ITEMS.forEach((blockItem, identifier) -> Registry.register(Registries.ITEM, identifier, blockItem));
    }
}
