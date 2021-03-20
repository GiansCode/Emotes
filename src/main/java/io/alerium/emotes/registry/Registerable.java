package io.alerium.emotes.registry;

import io.alerium.emotes.EmotesPlugin;

public interface Registerable {

    void enable(final EmotesPlugin plugin);

    default void disable(final EmotesPlugin plugin) {}

}
