package dev.zac99lol.originscontent.mixin.client.silly_mode;

import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.border.WorldBorder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    @Redirect(method = {"attackBlock", "breakBlock", "attackEntity", "interactBlock", "interactEntity", "interactEntityAtLocation", "interactItem", "interactBlockInternal"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/border/WorldBorder;contains(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean originscontent$silly(WorldBorder instance, BlockPos pos) {
        return instance.contains(pos) || OriginsContent.SILLY_MODE;
    }
}
