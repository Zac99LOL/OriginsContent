package dev.zac99lol.originscontent.util;

public interface PlayerAttackHeld {
    default boolean originscontent$isHoldingAttack() {
        return false;
    }

    default void originscontent$setHoldingAttack(boolean attackHeld) {

    }
}
