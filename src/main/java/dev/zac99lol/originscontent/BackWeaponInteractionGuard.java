package dev.zac99lol.originscontent;

import dev.doctor4t.arsenal.cca.BackWeaponComponent;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

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