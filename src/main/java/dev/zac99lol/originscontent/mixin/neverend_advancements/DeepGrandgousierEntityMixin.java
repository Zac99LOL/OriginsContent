package dev.zac99lol.originscontent.mixin.neverend_advancements;

import dev.zac99lol.neverend.entity.DeepGrandgousierEntity;
import dev.zac99lol.neverend.entity.GrandgousierEntity;
import dev.zac99lol.originscontent.init.ModAdvancements;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DeepGrandgousierEntity.class)
public abstract class DeepGrandgousierEntityMixin extends GrandgousierEntity {
    public DeepGrandgousierEntityMixin(EntityType<? extends GrandgousierEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            Advancement advancement = serverWorld.getServer().getAdvancementLoader().get(ModAdvancements.WITNESS);
            PlayerLookup.around(serverWorld, this.getPos(), 64).stream().filter(player -> player.equals(this.getTarget())).forEach(player -> player.getAdvancementTracker().grantCriterion(advancement, "nohappen"));
        }
        return super.tryAttack(target);
    }
}
