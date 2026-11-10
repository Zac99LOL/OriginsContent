package dev.zac99lol.originscontent.mixin.combat_log;

import dev.zac99lol.originscontent.CombatLogUtil;
import dev.zac99lol.originscontent.init.ModPackets;
import dev.zac99lol.originscontent.util.CanCombatLog;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements CanCombatLog {
    @Shadow
    private boolean disconnected;
    @Shadow
    public ServerPlayNetworkHandler networkHandler;

    @Shadow
    public abstract ServerWorld getServerWorld();

    @Unique
    private boolean executedDisconnect = false;
    @Unique
    private long outOfCombatTime = 0L;
    @Unique
    private Runnable delayedTask;
    @Unique
    private long delayedTaskTime;

    @Override
    public boolean originscontent$canDisconnect() {
        return this.outOfCombatTime != -1L && this.outOfCombatTime <= System.currentTimeMillis();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void originscontent$setInCombatUntil(long time) {
        if (this.originscontent$canDisconnect()) ((ServerPlayerEntity) (Object) this).sendMessage(Text.translatable("text.originscontent.start_combat").formatted(Formatting.RED), true);
        this.outOfCombatTime = time;
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeVarLong(time);
        ServerPlayNetworking.send((ServerPlayerEntity) (Object) this, ModPackets.UPDATE_OUT_OF_COMBAT_TIME, buf);
        this.originscontent$delay(time, () -> ((ServerPlayerEntity) (Object) this).sendMessage(Text.translatable("text.originscontent.end_combat").formatted(Formatting.GREEN), true));
    }

    @Override
    public boolean originscontent$isFake() {
        return this.disconnected;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void originscontent$onRealDisconnect() {
        this.disconnected = true;

        if (!this.originscontent$canDisconnect()) {
            PlayerLookup.around(this.getServerWorld(), ((ServerPlayerEntity) (Object) this).getPos(), 64).forEach(player -> player.sendMessage(Text.literal(String.format("%s combat logged", ((ServerPlayerEntity) (Object) this).getDisplayName().getString())).formatted(Formatting.YELLOW)));
            DISCONNECTED_PLAYERS.add((ServerPlayerEntity) (Object) this);
        }
    }

    @Override
    public void originscontent$setAllowDisconnect(boolean allow) {
        this.outOfCombatTime = allow ? 0 : -1;
    }

    @Override
    public void originscontent$delay(long until, Runnable task) {
        this.delayedTaskTime = until;
        this.delayedTask = task;
    }

    @Inject(method = "isDisconnected", at = @At("HEAD"), cancellable = true)
    private void originscontent$notYet(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(this.originscontent$canDisconnect() && this.disconnected);
    }

    @Inject(method = "playerTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;getInventory()Lnet/minecraft/entity/player/PlayerInventory;"), cancellable = true)
    private void originscontent$tick(CallbackInfo ci) {
        if (this.originscontent$isFake()) {
            if (this.originscontent$canDisconnect() && !this.executedDisconnect) {
                this.networkHandler.disconnect(Text.empty());
                this.executedDisconnect = true;
            }

            ci.cancel();
        } else if (this.delayedTask != null && this.delayedTaskTime <= System.currentTimeMillis()) {
            this.delayedTask.run();
            this.delayedTask = null;
        }
    }

    @Inject(method = "onDisconnect", at = @At("TAIL"))
    private void originscontent$disconnect(CallbackInfo ci) {
        DISCONNECTED_PLAYERS.remove((ServerPlayerEntity) (Object) this);
    }

    @Inject(method = "damage", at = @At("TAIL"))
    private void originscontent$triggerDamaged(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        CombatLogUtil.onDamaged((ServerPlayerEntity) (Object) this, source);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "onDeath", at = @At("RETURN"))
    private void originscontent$saveDeathMessage(DamageSource source, CallbackInfo ci) {
        if (((CanCombatLog) this).originscontent$isFake()) {
            boolean seeDeathMsgs = this.getServerWorld().getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES);
            Text deathMsg;
            if (seeDeathMsgs) {
                deathMsg = ((ServerPlayerEntity) (Object) this).getDamageTracker().getDeathMessage();
                if (deathMsg.getString().length() > 256) {
                    String string = deathMsg.asTruncatedString(256);
                    MutableText attackTooLongMsg = Text.translatable("death.attack.message_too_long", Text.literal(string).formatted(Formatting.YELLOW));
                    deathMsg = Text.translatable("death.attack.even_more_magic", ((ServerPlayerEntity) (Object) this).getDisplayName()).styled((style) -> style.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, attackTooLongMsg)));
                }
            } else {
                deathMsg = ScreenTexts.EMPTY;
            }

            CanCombatLog.SKIPPED_DEATH_MESSAGES.put(((ServerPlayerEntity) (Object) this).getUuid(), deathMsg);
        }
    }
}
