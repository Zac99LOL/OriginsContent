package dev.zac99lol.originscontent;

import dev.zac99lol.originscontent.config.ModConfig;
import dev.zac99lol.originscontent.init.ModPackets;
import dev.zac99lol.originscontent.util.CanCombatLog;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class CombatLogUtil {
    private static ModConfig CONFIG;

    public static void initialize() {
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).get();
        AttackEntityCallback.EVENT.register(CombatLogUtil::onAttack);
        ServerLivingEntityEvents.AFTER_DEATH.register(CombatLogUtil::onDeath);
        ServerPlayConnectionEvents.JOIN.register(CombatLogUtil::onPlayerJoin);
    }

    private static ActionResult onAttack(PlayerEntity attacker, World level, Hand hand, Entity entity, EntityHitResult hitResult) {
        if (entity instanceof CanCombatLog && !entity.equals(attacker)) {
            long allowedDisconnectTime = System.currentTimeMillis() + Math.round(CONFIG.combatTimeout * 1000.0F);
            ((CanCombatLog) entity).originscontent$setInCombatUntil(allowedDisconnectTime);

            ((CanCombatLog) attacker).originscontent$setInCombatUntil(allowedDisconnectTime);
        }

        return ActionResult.PASS;
    }

    public static void onDeath(LivingEntity entity, DamageSource damageSource) {
        if (entity instanceof CanCombatLog player) {
            if (player.originscontent$isFake()) {
                ((ServerPlayerEntity) player).networkHandler.onDisconnected(Text.empty());
            }
        }
    }

    public static void onDamaged(ServerPlayerEntity target, DamageSource damageSource) {
        long allowedDisconnectTime = System.currentTimeMillis() + Math.round(CONFIG.combatTimeout * 1000);
        if (damageSource.getAttacker() instanceof ProjectileEntity p && p.getOwner() instanceof ServerPlayerEntity attacker && !target.equals(attacker)) {
            ((CanCombatLog) attacker).originscontent$setInCombatUntil(allowedDisconnectTime);
            ((CanCombatLog) target).originscontent$setInCombatUntil(allowedDisconnectTime);
        } else if (damageSource.getAttacker() instanceof PlayerEntity p && !target.equals(p)) {
            ((CanCombatLog) target).originscontent$setInCombatUntil(allowedDisconnectTime);
        }
    }

    public static void onPlayerJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeDouble(CONFIG.combatTimeout);
        sender.sendPacket(ModPackets.UPDATE_COMBAT_DURATION, buf);

        Text deathMessage = CanCombatLog.SKIPPED_DEATH_MESSAGES.get(handler.player.getUuid());
        if (deathMessage != null) {
            handler.player.sendMessage(deathMessage, false);
            handler.sendPacket(new DeathMessageS2CPacket(handler.player.getId(), deathMessage));
            CanCombatLog.SKIPPED_DEATH_MESSAGES.remove(handler.player.getUuid());
        }
    }
}
