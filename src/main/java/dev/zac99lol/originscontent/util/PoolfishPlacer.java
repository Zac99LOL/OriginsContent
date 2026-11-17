package dev.zac99lol.originscontent.util;

import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

public interface PoolfishPlacer {
    @Nullable PlayerEntity originscontent$getPlacer();
    void originscontent$setPlacer(PlayerEntity placer);
}
