package dev.zac99lol.originscontent.init;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.zac99lol.originscontent.cca.HoldingComponent;

public class ModCardinalComponents implements EntityComponentInitializer {
    public static final ComponentKey<HoldingComponent> HOLDING = ComponentRegistry.getOrCreate(ModPackets.HOLDING, HoldingComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(HOLDING, HoldingComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
