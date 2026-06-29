package dev.zac99lol.originscontent.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Items.class)
public abstract class ItemsMixin {

    private static final FoodComponent ANCIENT_DEBRIS_FOOD = new FoodComponent.Builder()
        .hunger(8)
        .saturationModifier(0.3f)
        .alwaysEdible()
        .build();

    @Redirect(
        method = "<clinit>",
        at = @At(
            value = "NEW",
            target = "(Lnet/minecraft/block/Block;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/BlockItem;"
        )
    )
    private static BlockItem redirectBlockItemConstructor(Block block, Item.Settings settings) {
        if (block == Blocks.ANCIENT_DEBRIS) {
            settings = settings.food(ANCIENT_DEBRIS_FOOD);
        }
        return new BlockItem(block, settings);
    }
}