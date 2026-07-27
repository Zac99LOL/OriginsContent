package dev.zac99lol.originscontent.mixin.client;

import com.mojang.datafixers.util.Pair;
import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.power.DisableOffhandPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Overrides the offhand slot's ghost icon at the call site inside
 * HandledScreen#drawSlot, rather than mixing Slot#getBackgroundSprite
 * directly. The offhand slot in PlayerScreenHandler is an anonymous Slot
 * subclass that already overrides getBackgroundSprite() itself (to show
 * the vanilla shield icon) - virtual dispatch calls that override
 * directly, so a @Mixin(Slot.class) injection on the base method is
 * never reached for this particular slot. Redirecting the call site
 * instead works regardless of which concrete Slot subclass is involved.
 * Client-only (HandledScreen only exists client-side).
 */
@Mixin(HandledScreen.class)
public abstract class HandledScreenSlotIconMixin {

    @Redirect(method = "drawSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;getBackgroundSprite()Lcom/mojang/datafixers/util/Pair;"))
    private Pair<Identifier, Identifier> originscontent$offhandDisallowedIcon(Slot slot) {
        if (slot.inventory instanceof PlayerInventory inv
            && slot.getIndex() == 40
            && PowerHolderComponent.hasPower(inv.player, DisableOffhandPower.class)) {
            return Pair.of(
                PlayerScreenHandler.BLOCK_ATLAS_TEXTURE,
                OriginsContent.id("item/disallowed_slot")
            );
        }
        return slot.getBackgroundSprite();
    }
}
