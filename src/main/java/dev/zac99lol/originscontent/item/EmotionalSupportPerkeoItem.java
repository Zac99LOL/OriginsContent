package dev.zac99lol.originscontent.item;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class EmotionalSupportPerkeoItem extends Item {
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public EmotionalSupportPerkeoItem(Item.Settings settings) {
        super(settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();

        builder.put(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,
            new EntityAttributeModifier(UUID.fromString("e2b4fc47-c352-4279-b0f3-cc4231b687ae"),
                "Weapon knockback modifier", (double) 3, EntityAttributeModifier.Operation.ADDITION));

        this.attributeModifiers = builder.build();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.Zac99LOL)) tooltip.add(Text.literal("Hi Zac!").formatted(Formatting.DARK_AQUA));
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.SlightlyShorter)) tooltip.add(Text.literal("Hi Harry!").formatted(Formatting.GOLD));
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.Astrulux)) tooltip.add(Text.literal("Hi Nathan!").formatted(Formatting.LIGHT_PURPLE));
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.Olified)) tooltip.add(Text.literal("Hello, Olified.").formatted(Formatting.DARK_BLUE));
        if (OriginsContent.heldByUUID(stack, OriginsContent.UUIDs.oxf957)) tooltip.add(Text.literal("Hi, oxf!").formatted(Formatting.DARK_AQUA));
        tooltip.add(Text.translatable("itemTooltip.originscontent.perkeo").formatted(Formatting.DARK_GRAY));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        float knockbackStrength = 3.0f;
        float yaw = attacker.getYaw() * ((float)Math.PI / 180F);
        target.takeKnockback(knockbackStrength, MathHelper.sin(yaw), -MathHelper.cos(yaw));

        return true;
    }
}