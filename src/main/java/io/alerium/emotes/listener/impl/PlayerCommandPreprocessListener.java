package io.alerium.emotes.listener.impl;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.util.message.Message;
import io.alerium.emotes.util.message.MessageType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;

public final class PlayerCommandPreprocessListener implements Listener {

    private final FileConfiguration configuration;

    public PlayerCommandPreprocessListener(final EmotesPlugin plugin) {
        this.configuration = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        final String command = event.getMessage();
        final Player player = event.getPlayer();

        if (!matches(command)) {
            return;
        }

        event.setCancelled(true);
        Message.send(
                player,
                MessageType.getSelectedType(configuration),
                configuration.getStringList("message.blacklisted-commands-restriction")
        );
    }

    private boolean matches(final String command) {
        final Set<String> commands = new HashSet<>(configuration.getStringList("settings.blacklisted-commands"));

        return commands.stream().anyMatch(it -> it.equalsIgnoreCase(command));
    }

}
