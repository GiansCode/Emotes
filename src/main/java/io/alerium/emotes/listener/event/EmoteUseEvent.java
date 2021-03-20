package io.alerium.emotes.listener.event;

import io.alerium.emotes.object.impl.Emote;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@SuppressWarnings("NullableProblems")
public final class EmoteUseEvent extends Event {

    private final Player player;
    private final Emote emote;

    public EmoteUseEvent(final Player player, final Emote emote) {
        this.player = player;
        this.emote = emote;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Emote getEmote() {
        return this.emote;
    }

    // Default Event Stuff

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
