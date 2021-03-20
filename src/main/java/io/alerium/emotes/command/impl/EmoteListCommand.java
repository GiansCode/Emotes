package io.alerium.emotes.command.impl;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.object.EmoteRegisterable;
import io.alerium.emotes.object.impl.Emote;
import io.alerium.emotes.util.message.Message;
import io.alerium.emotes.util.message.MessageType;
import io.alerium.emotes.util.message.Replace;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Command("emotes")
public final class EmoteListCommand extends CommandBase {

    private final FileConfiguration configuration;
    private final EmoteRegisterable registerable;

    public EmoteListCommand(final EmotesPlugin plugin) {
        this.configuration = plugin.getConfig();
        this.registerable = plugin.getEmoteRegisterable();
    }

    @SubCommand("list")
    @Permission("emotes.commands.list")
    public void onListCommand(final Player player) {
        Message.send(
                player,
                MessageType.getSelectedType(configuration),
                Replace.replaceList(
                        configuration.getStringList("message.list-command"),
                        "{emotes_list}", getStringedEmotes(),
                        "{emotes_amount}", String.valueOf(registerable.getEmoteRegistry().size())
                )
        );
    }

    private String getStringedEmotes() {
        final StringBuilder builder = new StringBuilder("&f");
        final Map<String, Emote> emotes = registerable.getEmoteRegistry();

        final List<Emote> emoteList = new ArrayList<>(emotes.values());
        for (int index = 0; index < emotes.size(); index++) {
            if (index == emotes.size() - 1) builder.append(emoteList.get(index).getIdentifier());
            else builder.append(emoteList.get(index).getIdentifier()).append("&7, &f");
        }

        return builder.toString();
    }

}
