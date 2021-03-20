package io.alerium.emotes.util.message;

import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.bukkit.entity.Player;

import java.util.List;

public final class Message {

    private static final BukkitMessage BUKKIT_MESSAGE = BukkitMessage.create();

    public static void send(final Player player, final MessageType type, final List<String> text) {
        if (text.isEmpty()) return;

        switch (type) {
            case CHAT -> text.forEach(it -> BUKKIT_MESSAGE.parse(it).sendMessage(player));
            case ACTION_BAR -> text.forEach(it -> BUKKIT_MESSAGE.parse(it).sendActionBar(player, 2, 5, 2));
            case TITLE -> text.forEach(it -> BUKKIT_MESSAGE.parse(it).sendTitle(player, 2, 5, 2));
            case SUBTITLE -> text.forEach(it -> BUKKIT_MESSAGE.parse(it).sendSubTitle(player, 2, 5, 2));
        }
    }

}
