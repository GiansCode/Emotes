package io.alerium.emotes.api;

import io.alerium.emotes.object.EmoteRegisterable;
import io.alerium.emotes.object.impl.Emote;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public final class EmoteAPI {

    private final EmoteRegisterable registerable;

    public EmoteAPI(final EmoteRegisterable registerable) {
        this.registerable = registerable;
    }

    public void addEmote(final Emote emote) {
        this.registerable.getEmoteRegistry().put(emote.getIdentifier(), emote);
    }

    public Set<Emote> getEmotes() {
        return new HashSet<>(this.registerable.getEmoteRegistry().values());
    }

    public boolean sendEmote(final Emote emote, final Player player) {
        return this.registerable.sendEmote(emote, player);
    }

}
