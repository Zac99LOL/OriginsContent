package dev.zac99lol.originscontent.util;

import dev.zac99lol.originscontent.OriginsContent;
import dev.zac99lol.originscontent.block.entity.MarshmallowCanBlockEntity;
import dev.zac99lol.originscontent.init.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import snownee.jade.api.Accessor;
import snownee.jade.api.view.*;

import java.util.List;

public enum MarshmallowCanProvider implements IServerExtensionProvider<Object, ItemStack>, IClientExtensionProvider<ItemStack, ItemView> {
    INSTANCE;

    @Override
    public List<ClientViewGroup<ItemView>> getClientGroups(Accessor<?> accessor, List<ViewGroup<ItemStack>> list) {
        return ClientViewGroup.map(list, ItemView::new, null);
    }

    @Override
    public @Nullable List<ViewGroup<ItemStack>> getGroups(ServerPlayerEntity serverPlayerEntity, ServerWorld serverWorld, Object target, boolean showDetails) {
        if (target instanceof MarshmallowCanBlockEntity marshmallowCanBlockEntity) {
            return List.of(new ViewGroup<>(List.of(new ItemStack(ModItems.MARSHMALLOW, marshmallowCanBlockEntity.getMarshmallowCount()))));
        } else {
            return null;
        }
    }

    @Override
    public Identifier getUid() {
        return OriginsContent.id("marshmallows");
    }
}
