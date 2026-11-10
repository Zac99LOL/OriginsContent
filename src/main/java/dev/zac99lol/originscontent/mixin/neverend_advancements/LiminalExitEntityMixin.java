package dev.zac99lol.originscontent.mixin.neverend_advancements;

import dev.zac99lol.neverend.entity.LiminalExitEntity;
import dev.zac99lol.originscontent.init.ModAdvancements;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LiminalExitEntity.class)
public abstract class LiminalExitEntityMixin {
    @Inject(method = "returnHome", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/dimension/v1/FabricDimensions;teleport(Lnet/minecraft/entity/Entity;Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/world/TeleportTarget;)Lnet/minecraft/entity/Entity;"))
    private static void originscontent$grant(PlayerEntity player, CallbackInfo ci) {
        Advancement advancement = Objects.requireNonNull(player.getServer()).getAdvancementLoader().get(ModAdvancements.ESCAPE);
        ((ServerPlayerEntity) player).getAdvancementTracker().grantCriterion(advancement, "nohappen");
    }
}
