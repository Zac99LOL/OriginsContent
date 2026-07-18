package dev.zac99lol.originscontent.api;

import doctor4t.defile.cca.DefileComponents;
import net.minecraft.server.world.ServerWorld;

public class DefileApi {
    public static boolean isBlackRainActive(ServerWorld world) {
        return DefileComponents.BLACK_RAIN.get(world).isRaining();
    }
}
