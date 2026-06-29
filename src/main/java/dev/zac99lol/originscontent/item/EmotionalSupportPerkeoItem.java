package dev.zac99lol.originscontent.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class EmotionalSupportPerkeoItem extends Item {

    private static final UUID ZAC_UUID = UUID.fromString("20aaa00a-d216-4ed8-9a3b-bd8a5638210a");
    private static final UUID HARRY_UUID = UUID.fromString("bd6f0aa4-dc8b-4a44-b1e5-36b7b68cc255");
    private static final UUID NATHAN_UUID = UUID.fromString("c90f16a6-60d2-4813-a6b4-15fc81971a03");
    private static final int MAX_COOLDOWN = 20;
    private static final String COOLDOWN_KEY = "PerkeoCooldown";

    public EmotionalSupportPerkeoItem(Item.Settings settings) {
        super(settings);
    }

    // --- per-stack cooldown helpers (NBT-backed, not instance fields) ---

    private static int getCooldown(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt == null || !nbt.contains(COOLDOWN_KEY)) return 0;
        return nbt.getInt(COOLDOWN_KEY);
    }

    private static void setCooldown(ItemStack stack, int value) {
        stack.getOrCreateNbt().putInt(COOLDOWN_KEY, value);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (heldByUUID(stack, ZAC_UUID)) tooltip.add(Text.literal("Hi Zac!").formatted(Formatting.DARK_AQUA));
        tooltip.add(Text.translatable("itemTooltip.originscontent.perkeo").formatted(Formatting.DARK_GRAY));
        if (heldByUUID(stack, HARRY_UUID)) tooltip.add(Text.literal("Hi Harry!").formatted(Formatting.GOLD));
        if (heldByUUID(stack, NATHAN_UUID)) tooltip.add(Text.literal("Hi Nathan!").formatted(Formatting.LIGHT_PURPLE));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (!(entity instanceof PlayerEntity player)) {
            return;
        }

        int cooldown = getCooldown(stack);
        if (cooldown > 0) {
            setCooldown(stack, cooldown - 1);
            if (cooldown - 1 == 0) stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, new EntityAttributeModifier("perkeo", 5, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (getCooldown(stack) == 0) {
            // Manual knockback boost, since GENERIC_ATTACK_KNOCKBACK doesn't apply to players
            double knockbackStrength = 2.5; // tune this — vanilla Knockback enchant uses ~0.5 per level
            double dx = target.getX() - attacker.getX();
            double dz = target.getZ() - attacker.getZ();

            // normalize so direction is consistent regardless of distance
            double dist = Math.sqrt(dx * dx + dz * dz);
            if (dist > 0.0001) {
                target.takeKnockback(knockbackStrength, -dx / dist, -dz / dist);
            }
        }
        setCooldown(stack, MAX_COOLDOWN);
        stack.removeSubNbt("AttributeModifiers");
        return true;
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return heldByUUID(stack, ZAC_UUID);
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round(13.0F - (float) getCooldown(stack) * 13.0F / (float) MAX_COOLDOWN);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return ColorHelper.Argb.getArgb(255, 0, 252, 206);
    }

    private boolean heldByUUID(ItemStack stack, UUID uuid) {
        if (!(stack.getHolder() instanceof PlayerEntity player)) return false;
        return player.getGameProfile().getId().equals(uuid);
    }
}