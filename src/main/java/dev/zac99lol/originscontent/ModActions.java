package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.action.entity.ShutdownAction;
import io.github.apace100.apoli.registry.ApoliRegistries;
import net.minecraft.registry.Registry;

public abstract class ModActions {
    public static void init() {
        Registry.register(ApoliRegistries.ENTITY_ACTION, OriginsContent.id("shutdown"), ShutdownAction.getFactory());
    }
}
