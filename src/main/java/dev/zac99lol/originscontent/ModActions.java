package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.action.entity.OpenSteamGameAction;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public abstract class ModActions {
    public static void init() {
        Registry.register(ApoliRegistries.ENTITY_ACTION, OriginsContent.id("open_steam_game"), OpenSteamGameAction.getFactory());
    }
}
