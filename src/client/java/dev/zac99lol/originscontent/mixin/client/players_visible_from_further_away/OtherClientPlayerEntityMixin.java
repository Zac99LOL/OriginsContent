package dev.zac99lol.originscontent.mixin.client.players_visible_from_further_away;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.network.OtherClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(OtherClientPlayerEntity.class)
public class OtherClientPlayerEntityMixin {
    @ModifyReturnValue(method = "shouldRender", at = @At("RETURN"))
    private boolean originscontent$alwaysRenderPlayers(boolean original) {
        return true;
    }
}
