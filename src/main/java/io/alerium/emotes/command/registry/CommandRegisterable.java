package io.alerium.emotes.command.registry;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.command.impl.EmoteListCommand;
import io.alerium.emotes.command.impl.EmoteUseCommand;
import io.alerium.emotes.command.impl.HelpCommand;
import io.alerium.emotes.command.impl.ReloadCommand;
import io.alerium.emotes.registry.Registerable;
import me.mattstudios.mf.base.CommandManager;

public final class CommandRegisterable implements Registerable {

    @Override
    public void enable(final EmotesPlugin plugin) {
        final CommandManager manager = new CommandManager(plugin);

        manager.register(
                new HelpCommand(plugin),
                new ReloadCommand(plugin),

                new EmoteListCommand(plugin),
                new EmoteUseCommand(plugin)
        );
    }

}
