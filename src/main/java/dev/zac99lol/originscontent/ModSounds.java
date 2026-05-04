// dev/zac99lol/originscontent/ModSounds.java
package dev.zac99lol.originscontent;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent DORMANT_CATALYST_ACTIVATE = register("dormant_catalyst_activate");
    public static final SoundEvent DORMANT_CATALYST_CONVERT = register("dormant_catalyst_convert");

    private static SoundEvent register(String name) {
        Identifier id = new Identifier(OriginsContent.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void initialize() {
        // Class load is enough, but this gives us a clean call site
    }
}