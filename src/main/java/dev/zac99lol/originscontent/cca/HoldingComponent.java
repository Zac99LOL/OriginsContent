package dev.zac99lol.originscontent.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.zac99lol.originscontent.init.ModCardinalComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class HoldingComponent implements AutoSyncedComponent {
    private final PlayerEntity player;
    private boolean holding = false;

    public HoldingComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        ModCardinalComponents.HOLDING.sync(this.player);
    }

    public boolean isHolding() {
        return this.holding;
    }

    public void setHolding(boolean holding) {
        this.holding = holding;
        this.sync();
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.holding = tag.getBoolean("holding");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("holding", this.holding);
    }
}
