package io.alerium.emotes.command.impl;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.util.message.Message;
import io.alerium.emotes.util.message.MessageType;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@Command("emotes")
public final class HelpCommand extends CommandBase {

    private final FileConfiguration configuration;

    public HelpCommand(final EmotesPlugin plugin) {
        this.configuration = plugin.getConfig();
    }

    @Default
    @Permission("emotes.commands.help")
    public void onHelpCommand(final Player player) {
        Message.send(
                player,
                MessageType.getSelectedType(configuration),
                configuration.getStringList("message.help-command")
        );
    }

}
