package dev.zac99lol.originscontent.mixin.client.neverend_changes;

import com.mojang.authlib.GameProfile;
import dev.zac99lol.neverend.index.ModDimension;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    public ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    @Override
    public boolean hasReducedDebugInfo() {
        return super.hasReducedDebugInfo() || clientWorld.getRegistryKey().equals(ModDimension.POOLROOMS_REGISTRY_KEY);
    }
}
