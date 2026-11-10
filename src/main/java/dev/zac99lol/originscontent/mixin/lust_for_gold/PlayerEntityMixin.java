package dev.zac99lol.originscontent.mixin.lust_for_gold;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.zac99lol.originscontent.OriginsContent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @SuppressWarnings("DataFlowIssue")
    @ModifyReturnValue(method = "getName", at = @At("RETURN"))
    private Text originscontent$lustForGold(Text original) {
        if (((PlayerEntity) (Object) this).getGameProfile().getId().equals(OriginsContent.UUIDs.GoldnHitz) && ((PlayerEntity) (Object) this).hasCustomName()) return ((PlayerEntity) (Object) this).getCustomName();
        return original;
    }
}
