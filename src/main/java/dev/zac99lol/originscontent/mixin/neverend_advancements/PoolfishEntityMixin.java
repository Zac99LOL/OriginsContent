package dev.zac99lol.originscontent.mixin.neverend_advancements;

import dev.zac99lol.neverend.entity.PoolfishEntity;
import dev.zac99lol.originscontent.util.PoolfishPlacer;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PoolfishEntity.class)
public abstract class PoolfishEntityMixin implements PoolfishPlacer {
    @Unique
    @Nullable
    PlayerEntity placer;

    @Override
    public @Nullable PlayerEntity originscontent$getPlacer() {
        return placer;
    }

    @Override
    public void originscontent$setPlacer(PlayerEntity placer) {
        this.placer = placer;
    }
}
