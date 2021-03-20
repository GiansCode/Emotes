package io.alerium.emotes.command.impl;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.util.TaskUtil;
import io.alerium.emotes.util.message.Message;
import io.alerium.emotes.util.message.MessageType;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@Command("emotes")
public final class ReloadCommand extends CommandBase {

    private final FileConfiguration configuration;
    private final EmotesPlugin plugin;

    public ReloadCommand(final EmotesPlugin plugin) {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();
    }

    @SubCommand("reload")
    @Permission("emotes.commands.reload")
    public void onReloadCommand(final Player player) {
        TaskUtil.async(() -> {

            plugin.getRegisterables().forEach(it -> it.disable(plugin));
            plugin.getRegisterables().forEach(it -> it.enable(plugin));

            Message.send(
                    player,
                    MessageType.getSelectedType(configuration),
                    configuration.getStringList("message.reload-command")
            );
        });
    }

}
