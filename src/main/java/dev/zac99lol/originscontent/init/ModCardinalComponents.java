package dev.zac99lol.originscontent.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.cca.PlayerSavedOriginComponent;

public class ModCardinalComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerSavedOriginComponent> SAVED_ORIGIN = ComponentRegistry.getOrCreate(OriginsContent.id("saved_origin"), PlayerSavedOriginComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SAVED_ORIGIN, PlayerSavedOriginComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }
}
