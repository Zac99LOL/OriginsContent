package dev.zac99lol.originscontent;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedBookBundleItem extends Item {
    public EnchantedBookBundleItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}
