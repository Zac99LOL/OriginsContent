package dev.zac99lol.originscontent.mixin.client;

import com.mojang.datafixers.util.Pair;
import dev.doctor4t.arsenal.util.BackSlot;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Cycles the empty back slot's ghost icon through a fixed set of weapon
 * silhouettes rather than showing a single static scythe outline.
 * Client-only (MinecraftClient only exists client-side).
 */
@Mixin(Slot.class)
public abstract class BackSlotMixin {

    @Unique
    private static final String[] CYCLE_WEAPONS = {
        "sword", "axe", "spear", "mace", "scythe",
        "anchorblade", "bow", "crossbow"
    };
    @Unique
    private static final int TICKS_PER_FRAME = 20;

    @Inject(method = "getBackgroundSprite", at = @At("HEAD"), cancellable = true)
    private void originscontent$scytheOutline(CallbackInfoReturnable<Pair<Identifier, Identifier>> cir) {
        if (!((Object) this instanceof BackSlot)) {
            return;
        }

        long time = MinecraftClient.getInstance().world != null
            ? MinecraftClient.getInstance().world.getTime()
            : 0;
        int index = (int) ((time / TICKS_PER_FRAME) % CYCLE_WEAPONS.length);
        String weapon = CYCLE_WEAPONS[index];

        cir.setReturnValue(Pair.of(
            PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
            Identifier.of("originscontent", "item/empty/" + weapon)
        ));
    }
}