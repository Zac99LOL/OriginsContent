package dev.zac99lol.originscontent.init;

import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModSoundEvents {
    Map<SoundEvent, Identifier> SOUND_EVENTS = new LinkedHashMap<>();
    SoundEvent AXOLOTL_CONVERT = createSoundEvent("entity.axolotl.convert");
    SoundEvent MUSIC_DISC_TSUNDERE = createSoundEvent("music_disc.tsundere");

    static void initialize() {
        SOUND_EVENTS.keySet().forEach(soundEvent -> Registry.register(Registries.SOUND_EVENT, SOUND_EVENTS.get(soundEvent), soundEvent));
    }

    private static SoundEvent createSoundEvent(String path) {
        SoundEvent soundEvent = SoundEvent.of(OriginsContent.id(path));
        SOUND_EVENTS.put(soundEvent, OriginsContent.id(path));
        return soundEvent;
    }
}
