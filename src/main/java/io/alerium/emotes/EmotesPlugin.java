package io.alerium.emotes;

import io.alerium.emotes.api.EmoteAPI;
import io.alerium.emotes.command.registry.CommandRegisterable;
import io.alerium.emotes.listener.registry.ListenerRegisterable;
import io.alerium.emotes.object.EmoteRegisterable;
import io.alerium.emotes.registry.Registerable;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class EmotesPlugin extends JavaPlugin {

    private static final Set<Registerable> REGISTERABLES = new HashSet<>(Arrays.asList(
            new CommandRegisterable(), new EmoteRegisterable(), new ListenerRegisterable()
    ));

    @Override
    public void onEnable() {
        saveDefaultConfig();

        REGISTERABLES.forEach(it -> it.enable(this));
        getServer().getServicesManager().register(
                EmoteAPI.class, new EmoteAPI(getEmoteRegisterable()),
                this, ServicePriority.Normal
        );
    }

    @Override
    public void onDisable() {
        reloadConfig();

        REGISTERABLES.forEach(it -> it.disable(this));
    }

    public Set<Registerable> getRegisterables() {
        return REGISTERABLES;
    }

    public EmoteRegisterable getEmoteRegisterable() {
        final Optional<EmoteRegisterable> result = REGISTERABLES.stream()
                .filter(it -> it instanceof EmoteRegisterable)
                .map(it -> (EmoteRegisterable) it)
                .findFirst();

        if (!result.isPresent())
            throw new RuntimeException("Failed to retrieve EmoteRegisterable!");

        return result.get();
    }

}
