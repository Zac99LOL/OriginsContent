package dev.zac99lol.originscontent.mixin.poolfish_item_tooltip_baby;

import dev.zac99lol.neverend.item.PoolfishItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(PoolfishItem.class)
public abstract class PoolfishItemMixin extends Item {
    @Shadow
    public static @Nullable NbtCompound getPoolfishTag(ItemStack stack) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    public PoolfishItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (getPoolfishTag(stack).getInt("Age") < 0) tooltip.add(Text.translatable("tooltip.originscontent.baby").setStyle(Style.EMPTY.withColor(0xCCD9FC)));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
