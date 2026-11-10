package dev.zac99lol.originscontent.mixin.neverend_advancements;

import com.llamalad7.mixinextras.sugar.Local;
import dev.zac99lol.neverend.Neverend;
import dev.zac99lol.originscontent.init.ModAdvancements;
import net.minecraft.advancement.Advancement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Neverend.class)
public abstract class NeverendMixin {
    @Inject(method = "lambda$onInitialize$0", at = @At(value = "INVOKE", target = "Ldev/zac99lol/neverend/entity/DeepGrandgousierEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private static void originscontent$grant(MinecraftServer server, CallbackInfo ci, @Local(name = "player") ServerPlayerEntity player) {
        Advancement advancement = server.getAdvancementLoader().get(ModAdvancements.LEVIATHAN);
        player.getAdvancementTracker().grantCriterion(advancement, "impossible");
    }
}
