package io.alerium.emotes.command.impl;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.listener.event.EmoteUseEvent;
import io.alerium.emotes.object.EmoteRegisterable;
import io.alerium.emotes.object.impl.Emote;
import io.alerium.emotes.util.message.Message;
import io.alerium.emotes.util.message.MessageType;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@Command("emote")
public final class EmoteUseCommand extends CommandBase {

    private final EmoteRegisterable registerable;
    private final FileConfiguration configuration;

    public EmoteUseCommand(final EmotesPlugin plugin) {
        this.configuration = plugin.getConfig();
        this.registerable = plugin.getEmoteRegisterable();
    }

    @Default
    public void onEmoteUseCommand(final Player player, final String[] arguments) {
        if (arguments.length == 0) return;
        final Emote emote = this.registerable.getEmoteRegistry().get(arguments[0].toLowerCase());

        if (emote == null) {
            Message.send(
                    player,
                    MessageType.getSelectedType(configuration),
                    configuration.getStringList("message.invalid-emote")
            );
            return;
        }

        final Player target;
        switch (arguments.length) {
            case 1:
                if (!player.hasPermission("emotes.commands.emote." + emote.getIdentifier())) {
                    Message.send(
                            player,
                            MessageType.getSelectedType(configuration),
                            configuration.getStringList("message.missing-permission")
                    );
                    return;
                }
                target = player;
                break;
            case 2:
                if (!player.hasPermission("emotes.commands.emotes.others")) {
                    Message.send(
                            player,
                            MessageType.getSelectedType(configuration),
                            configuration.getStringList("message.missing-permission")
                    );
                    return;
                }

                target = Bukkit.getPlayer(arguments[1]);
                break;
            default:
                target = player;
        }

        if (target == null) {
            Message.send(
                    player,
                    MessageType.getSelectedType(configuration),
                    configuration.getStringList("message-invalid-target")
            );
            return;
        }

        emote.send(target);
        Bukkit.getServer().getPluginManager().callEvent(new EmoteUseEvent(player, emote));
    }

}
