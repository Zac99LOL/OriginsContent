package dev.zac99lol.originscontent.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class EmotionalSupportPerkeoItem extends Item {
    public EmotionalSupportPerkeoItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.Zac99LOL)) tooltip.add(Text.literal("Hi Zac!").formatted(Formatting.DARK_AQUA));
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.SlightlyShorter)) tooltip.add(Text.literal("Hi Harry!").formatted(Formatting.GOLD));
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.Astrulux)) tooltip.add(Text.literal("Hi Nathan!").formatted(Formatting.LIGHT_PURPLE));
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.Olified)) tooltip.add(Text.literal("Hello, Olified.").formatted(Formatting.DARK_BLUE));
        tooltip.add(Text.translatable("itemTooltip.originscontent.perkeo").formatted(Formatting.DARK_GRAY));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(
            EntityAttributes.GENERIC_ATTACK_KNOCKBACK,
            new EntityAttributeModifier(UUID.fromString("e2b4fc47-c352-4279-b0f3-cc4231b687ae"), "Weapon modifier",
                5, EntityAttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}