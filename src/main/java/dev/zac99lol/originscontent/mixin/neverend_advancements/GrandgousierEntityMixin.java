package dev.zac99lol.originscontent.mixin.neverend_advancements;

import dev.zac99lol.neverend.entity.GrandgousierEntity;
import dev.zac99lol.neverend.entity.PoolfishEntity;
import dev.zac99lol.originscontent.init.ModAdvancements;
import dev.zac99lol.originscontent.util.PoolfishPlacer;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(GrandgousierEntity.class)
public abstract class GrandgousierEntityMixin extends WaterCreatureEntity {
    protected GrandgousierEntityMixin(EntityType<? extends WaterCreatureEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setTarget", at = @At("HEAD"))
    private void originscontent$grant(LivingEntity target, CallbackInfo ci) {
         if (target instanceof PoolfishEntity poolfish
            && ((PoolfishPlacer) poolfish).originscontent$getPlacer() instanceof ServerPlayerEntity player
        ) {
            Advancement advancement = Objects.requireNonNull(player.getServer()).getAdvancementLoader().get(ModAdvancements.DISTRACT);
            player.getAdvancementTracker().grantCriterion(advancement, "nohappen");
        }
    }
}
