package dev.zac99lol.originscontent.mixin.neverend_advancements;

import dev.zac99lol.neverend.index.ModEntities;
import dev.zac99lol.originscontent.init.ModAdvancements;
import net.minecraft.advancement.Advancement;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Shadow
    @Final
    public MinecraftServer server;
    @Unique
    private int lookedAtTicks = 0;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancement/PlayerAdvancementTracker;sendUpdate(Lnet/minecraft/server/network/ServerPlayerEntity;)V"))
    private void originscontent$grant(CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        Vec3d eyePosition = player.getEyePos();
        Vec3d lookVector = player.getRotationVector();
        Vec3d reachVector = eyePosition.add(lookVector.x * 128, lookVector.y * 128, lookVector.z * 128);
        Box searchBox = player.getBoundingBox().stretch(lookVector.multiply(128)).expand(1);

        EntityHitResult hitResult = ProjectileUtil.getEntityCollision(player.getWorld(), player, eyePosition, reachVector, searchBox, target -> target.getType() == ModEntities.GRANDGOUSIER);

        if (hitResult != null) {
            Vec3d hitPos = hitResult.getPos();
            BlockHitResult blockHit = player.getWorld().raycast(new RaycastContext(eyePosition, hitPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, player));

            if (blockHit.getType() == HitResult.Type.MISS || eyePosition.distanceTo(blockHit.getPos()) > eyePosition.distanceTo(hitPos)) {
                lookedAtTicks++;
                if (lookedAtTicks >= 30) {
                    Advancement advancement = server.getAdvancementLoader().get(ModAdvancements.GRANDGOUSIER);
                    player.getAdvancementTracker().grantCriterion(advancement, "nohappen");
                }
            }
        } else {
            lookedAtTicks = 0;
        }
    }
}
