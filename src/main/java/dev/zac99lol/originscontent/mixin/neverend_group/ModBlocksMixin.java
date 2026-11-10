package dev.zac99lol.originscontent.mixin.neverend_group;

import dev.zac99lol.neverend.index.ModBlocks;
import dev.zac99lol.originscontent.OriginsContent;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModBlocks.class)
public interface ModBlocksMixin {
    @Inject(method = "createBlock", at = @At("HEAD"))
    private static <T extends Block> void originscontent$registerItemGroup(String name, T block, boolean createItem, CallbackInfoReturnable<T> cir) {
        if (createItem) ItemGroupEvents.modifyEntriesEvent(RegistryKey.of(RegistryKeys.ITEM_GROUP, OriginsContent.id("neverend_group"))).register(entries -> entries.add(block));
    }
}
