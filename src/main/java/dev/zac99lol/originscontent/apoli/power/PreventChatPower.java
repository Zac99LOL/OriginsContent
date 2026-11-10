package dev.zac99lol.originscontent.apoli.power;

import dev.zac99lol.originscontent.OriginsContent;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;

public class PreventChatPower extends Power {
    private final boolean preventChat;
    private final boolean preventCommand;

    public PreventChatPower(PowerType<?> type, LivingEntity entity, boolean preventChat, boolean preventCommand) {
        super(type, entity);
        this.preventChat = preventChat;
        this.preventCommand = preventCommand;
    }

    public boolean preventsChat() {
        return this.isActive() && preventChat;
    }

    public boolean preventsCommands() {
        return this.isActive() && preventCommand;
    }

    public static PowerFactory<?> getFactory() {
        return new PowerFactory<PreventChatPower>(OriginsContent.id("prevent_chat"),
            new SerializableData()
                .add("prevent_chat", SerializableDataTypes.BOOLEAN, true)
                .add("prevent_command", SerializableDataTypes.BOOLEAN, false),
            (data) -> (type, entity) -> new PreventChatPower(type, entity, data.getBoolean("prevent_chat"), data.getBoolean("prevent_command"))).allowCondition();
    }
}
