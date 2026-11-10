package dev.zac99lol.originscontent.api;

import doctor4t.defile.cca.DefileComponents;
import doctor4t.defile.cca.WorldBlackRainComponent;
import doctor4t.defile.cca.WorldEclipseAnimationComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DefileApi {
    public static boolean isBlackRainActive(World world) {
        return DefileComponents.BLACK_RAIN.get(world).isRaining();
    }

    public static void setBlackRain(World world, int durationTicks) {
        WorldBlackRainComponent blackRainComponent = DefileComponents.BLACK_RAIN.get(world);

        blackRainComponent.setTicks(durationTicks);
        if (durationTicks > 0) {
            blackRainComponent.setGradient(1.0F);
            blackRainComponent.setThunderGradient(1.0F);
        }
    }

    public static void startEclipse(World world, BlockPos pos) {
        WorldEclipseAnimationComponent eclipseComponent = DefileComponents.ECLIPSE_ANIMATION.get(world);

        eclipseComponent.setPosition(pos.toCenterPos());
        eclipseComponent.setTicks(0);
        eclipseComponent.start();
    }

    public static void triggerThunder(World world) {
        WorldBlackRainComponent blackRainComponent = DefileComponents.BLACK_RAIN.get(world);

        if (!blackRainComponent.isThundering()) return;
        blackRainComponent.setThunderGradient(1.0F);
    }
}
