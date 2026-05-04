// dev/zac99lol/originscontent/block/ModBlocks.java
package dev.zac99lol.originscontent.block;

import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModBlocks {

    public static final Block DORMANT_CATALYST = new DormantCatalystBlock(
        FabricBlockSettings.create()
            .strength(3.0f, 3.0f)
            .sounds(BlockSoundGroup.DEEPSLATE)
            .luminance(2)
            .nonOpaque()
    );

    public static void initialize() {
        Registry.register(Registries.BLOCK,
            new Identifier(OriginsContent.MOD_ID, "dormant_catalyst"),
            DORMANT_CATALYST);

        // Register block item so it can exist in inventory if needed
        Registry.register(Registries.ITEM,
            new Identifier(OriginsContent.MOD_ID, "dormant_catalyst"),
            new BlockItem(DORMANT_CATALYST, new FabricItemSettings().rarity(Rarity.UNCOMMON)));
    }
}