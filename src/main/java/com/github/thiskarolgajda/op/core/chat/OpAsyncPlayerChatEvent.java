package com.github.thiskarolgajda.op.core.chat;

import com.github.thiskarolgajda.op.utils.OpEvent;
import lombok.Getter;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
public class OpAsyncPlayerChatEvent extends OpEvent {
    private final Player player;
    private final BaseComponent message;
    private final BaseComponent textStartComponent;
    private final List<BaseComponent> messageComponents;

    public OpAsyncPlayerChatEvent(Player player, BaseComponent message, BaseComponent textStartComponent, List<BaseComponent> messageComponents) {
        super(true);
        this.message = message;
        this.player = player;
        this.textStartComponent = textStartComponent;
        this.messageComponents = messageComponents;
    }
}
