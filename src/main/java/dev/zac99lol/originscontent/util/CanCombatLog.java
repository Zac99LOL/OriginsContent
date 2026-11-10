package dev.zac99lol.originscontent.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.*;

public interface CanCombatLog {
    Set<ServerPlayerEntity> DISCONNECTED_PLAYERS = new HashSet<>();
    Map<UUID, Text> SKIPPED_DEATH_MESSAGES = new HashMap<>();

    boolean originscontent$canDisconnect();
    void originscontent$setInCombatUntil(long time);
    boolean originscontent$isFake();
    void originscontent$onRealDisconnect();
    void originscontent$setAllowDisconnect(boolean allow);
    void originscontent$delay(long until, Runnable task);
}
