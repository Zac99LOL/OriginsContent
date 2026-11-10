package dev.zac99lol.originscontent;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.EmiStack;
import dev.zac99lol.originscontent.init.ModItems;
import net.minecraft.item.ItemConvertible;

public class OriginsContentEmiPlugin implements EmiPlugin {
    static ItemConvertible[] TO_BE_REMOVED = {
        ModItems.POOLFISH_ON_A_STICK,
        ModItems.ACHIEVEMENT_ITEM,
        ModItems.ACHIEVEMENT_USE_ITEM,
        ModItems.BLUEPRINT_ITEM,
        ModItems.PLACEHOLDER_ITEM_1,
        ModItems.PLACEHOLDER_ITEM_2,
        ModItems.PLACEHOLDER_ITEM_3,
        ModItems.PLACEHOLDER_ITEM_4,
        ModItems.PLACEHOLDER_ITEM_5,
        ModItems.PLACEHOLDER_ITEM_6,
        ModItems.PLACEHOLDER_ITEM_7,
        ModItems.PLACEHOLDER_ITEM_8,
        ModItems.PLACEHOLDER_ITEM_9,
        ModItems.MODEL_ITEM
    };

    @Override
    public void register(EmiRegistry registry) {
        for (ItemConvertible item : TO_BE_REMOVED) {
            registry.removeEmiStacks(EmiStack.of(item));
        }
    }
}
