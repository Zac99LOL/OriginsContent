package dev.zac99lol.originscontent.client;

import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class SelectedSlotSyncClient {
    private static final Map<UUID, Integer> selectedSlots = new HashMap<>();

    public static void set(UUID playerId, int slot) {
        selectedSlots.put(playerId, slot);
    }

    public static int get(UUID playerId) {
        return selectedSlots.getOrDefault(playerId, 0);
    }

    public static int get(PlayerEntity player) {
        return get(player.getUuid());
    }
}
