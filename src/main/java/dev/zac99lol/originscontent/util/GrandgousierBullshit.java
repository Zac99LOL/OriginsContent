package dev.zac99lol.originscontent.util;

import dev.zac99lol.neverend.entity.GrandgousierEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;

public final class GrandgousierBullshit {
    public static final TrackedData<Boolean> SADDLED = DataTracker.registerData(GrandgousierEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> AUTO_TARGETING = DataTracker.registerData(GrandgousierEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
}
