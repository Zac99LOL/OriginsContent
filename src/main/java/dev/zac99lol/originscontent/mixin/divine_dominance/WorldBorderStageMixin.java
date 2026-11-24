package dev.zac99lol.originscontent.mixin.divine_dominance;

import net.minecraft.world.border.WorldBorderStage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(WorldBorderStage.class)
public enum WorldBorderStageMixin {
    ORIGINSCONTENT_DIVINE_DOMINANCE_SHRINKING(0xf5ce42),
    ORIGINSCONTENT_DIVINE_DOMINANCE_KABLAMMO(0xf56642);

    @Shadow
    WorldBorderStageMixin(int color) {
    }
}
