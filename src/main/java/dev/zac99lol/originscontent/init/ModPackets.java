package dev.zac99lol.originscontent.init;

import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.util.Identifier;

public interface ModPackets {
    Identifier SYNC_SELECTED_SLOT = OriginsContent.id("sync_selected_slot_packet");
    Identifier OPEN_STEAM_GAME = OriginsContent.id("open_steam_game_packet");
    Identifier CRASH_GAME = OriginsContent.id("crash_game_packet");
    Identifier UPDATE_OUT_OF_COMBAT_TIME = OriginsContent.id("update_out_of_combat_time_packet");
    Identifier UPDATE_COMBAT_DURATION = OriginsContent.id("update_combat_duration_packet");
    Identifier DISPLAY_TOAST = OriginsContent.id("display_toast_packet");
    Identifier OPEN_F3 = OriginsContent.id("open_f3_packet");
    Identifier SCREENSHAKE = OriginsContent.id("screenshake_packet");
    Identifier POSITIONED_SCREENSHAKE = OriginsContent.id("positioned_screenshake_packet");
}
