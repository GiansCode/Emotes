package io.alerium.emotes.listener.registry;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.listener.PlayerClickListener;
import io.alerium.emotes.listener.PlayerLeaveListener;
import io.alerium.emotes.registry.Registerable;

import java.util.stream.Stream;

public final class ListenerRegisterable implements Registerable {

    @Override
    public void enable(final EmotesPlugin plugin) {
        Stream.of(
                new PlayerClickListener(),
                new PlayerLeaveListener(plugin)
        ).forEach(it ->
                plugin.getServer().getPluginManager().registerEvents(it, plugin)
        );
    }
}
