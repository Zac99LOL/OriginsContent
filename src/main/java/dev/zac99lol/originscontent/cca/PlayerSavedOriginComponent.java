package dev.zac99lol.originscontent.cca;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.origin.OriginRegistry;
import io.github.apace100.origins.registry.ModComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class PlayerSavedOriginComponent implements Component {
    private final PlayerEntity entity;
    private Identifier origin;
    private OriginComponent originComponent = null;

    public PlayerSavedOriginComponent(PlayerEntity entity) {
        this.entity = entity;
        this.origin = Origin.EMPTY.getIdentifier();
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        this.origin = Identifier.tryParse(nbt.getString("Origin"));
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        this.originComponent = ModComponents.ORIGIN.get(entity);
        nbt.putString("Origin", this.origin.toString());
    }

    public void reset() {
        originComponent.setOrigin(OriginLayers.getLayer(OriginsContent.BASE_ORIGIN_LAYER), OriginRegistry.get(this.origin));
        this.origin = Origin.EMPTY.getIdentifier();
    }

    public void set() {
        this.origin = originComponent.getOrigin(OriginLayers.getLayer(OriginsContent.BASE_ORIGIN_LAYER)).getIdentifier(); // OriginComponentOrigin.get(this.entity).get(OriginLayers.getLayer(OriginsContent.BASE_ORIGIN_LAYER)).getIdentifier();
        originComponent.setOrigin(OriginLayers.getLayer(OriginsContent.BASE_ORIGIN_LAYER), OriginRegistry.get(Identifier.tryParse("neverend:level1")));
        originComponent.sync();
        OriginComponent.partialOnChosen(this.entity, true, OriginRegistry.get(Identifier.tryParse("neverend:level1")));
    }
}
