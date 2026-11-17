package dev.zac99lol.originscontent.init;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.block.entity.MarshmallowCanBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModBlockEntities {
    Map<BlockEntityType<? extends BlockEntity>, Identifier> BLOCK_ENTITIES = new LinkedHashMap<>();

    BlockEntityType<MarshmallowCanBlockEntity> MARSHMALLOW_CAN = createBlockEntity("marshmallow_can", FabricBlockEntityTypeBuilder.create(MarshmallowCanBlockEntity::new, ModBlocks.MARSHMALLOW_CAN).build());

    private static <T extends BlockEntityType<? extends BlockEntity>> T createBlockEntity(String id, T blockEntity) {
        BLOCK_ENTITIES.put(blockEntity, OriginsContent.id(id));
        return blockEntity;
    }

    static void initialize() {
        BLOCK_ENTITIES.forEach((blockEntityType, identifier) -> Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, blockEntityType));
    }
}
