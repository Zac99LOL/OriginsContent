package dev.zac99lol.originscontent.entity.ai;

import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class BullyPlayerGoal extends MeleeAttackGoal {
    private final UUID player;

    public BullyPlayerGoal(PathAwareEntity mob, double speed, UUID player) {
        super(mob, speed, false);
        this.player = player;
    }

    @Override
    public boolean canStart() {
        return (mob.getTarget() instanceof PlayerEntity p) && p.getUuid().equals(player) && super.canStart();
    }
}
