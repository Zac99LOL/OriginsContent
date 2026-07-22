package dev.zac99lol.originscontent;

import dev.doctor4t.arsenal.cca.BackWeaponComponent;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

/**
 * Patches dupe glitches caused by Arsenal's back weapon slot being able to
 * simultaneously register as the player's held hotbar item. When that
 * happens, vanilla interaction code can consume/transfer the back-slot
 * item without ever clearing it, duplicating it.
 * <p>
 * This blocks the two known transfer vectors (block placement, non-player
 * entity interaction e.g. armor stands) while a back weapon is held.
 * Combat and player-to-player interaction are intentionally untouched.
 * Blocks in {@link #UI_WHITELIST} (crafting table, furnaces, chests, etc)
 * are exempt since their interaction only opens a UI and never consumes
 * the held item.
 */
public class BackWeaponInteractionGuard {
    public static final TagKey<Block> UI_WHITELIST = TagKey.of(
        RegistryKeys.BLOCK,
        Identifier.of(OriginsContent.MOD_ID, "allowed_back_slot_interactions")
    );

    public static void init() {
        OriginsContent.LOGGER.info("Registering back weapon interaction guard...");

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient) return ActionResult.PASS;
            if (!BackWeaponComponent.isHoldingBackWeapon(player)) return ActionResult.PASS;
            Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            if (block.getRegistryEntry().isIn(UI_WHITELIST)) return ActionResult.PASS;
            return ActionResult.FAIL;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient) return ActionResult.PASS;
            if (entity instanceof PlayerEntity) return ActionResult.PASS;
            if (!BackWeaponComponent.isHoldingBackWeapon(player)) return ActionResult.PASS;
            return ActionResult.FAIL;
        });
    }
}