package io.alerium.emotes.object;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.object.impl.Emote;
import io.alerium.emotes.registry.Registerable;
import io.alerium.emotes.util.time.TimeAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public final class EmoteRegisterable implements Registerable {

    private static final Map<String, Emote> EMOTE_REGISTRY = new HashMap<>();
    private static final Map<UUID, ItemStack> ITEM_REGISTRY = new HashMap<>();
    private Cache<UUID, Boolean> cooldownCache;

    @Override
    public void enable(final EmotesPlugin plugin) {
        final FileConfiguration configuration = plugin.getConfig();
        cooldownCache = CacheBuilder.newBuilder()
                .expireAfterWrite(new TimeAPI().parseTime(configuration.getString("cooldown", "5s")).to(TimeUnit.SECONDS), TimeUnit.SECONDS)
                .build();

        final ConfigurationSection emotesSection = configuration.getConfigurationSection("emotes");

        if (emotesSection == null) {
            return;
        }

        for (final String key : emotesSection.getKeys(false)) {
            final ConfigurationSection emoteSection = emotesSection.getConfigurationSection(key);

            if (emoteSection == null) {
                continue;
            }

            final Emote emote = new Emote(
                    key,
                    new TimeAPI().parseTime(emoteSection.getString("update-rate", "1s")),
                    new LinkedList<>(emoteSection.getStringList("frames"))
            );

            EMOTE_REGISTRY.put(key, emote);
        }
    }

    @Override
    public void disable(final EmotesPlugin plugin) {
        EMOTE_REGISTRY.clear();
    }

    public boolean sendEmote(final Emote emote, final Player player) {
        final Boolean cooldown = this.cooldownCache.getIfPresent(player.getUniqueId());

        if (cooldown != null) {
            return false;
        }

        emote.send(player);
        if (!player.hasPermission("emotes.cooldown.bypass"))
            this.cooldownCache.put(player.getUniqueId(), true);
        return true;
    }

    public Map<UUID, ItemStack> getItemRegistry() {
        return ITEM_REGISTRY;
    }

    public Map<String, Emote> getEmoteRegistry() {
        return EMOTE_REGISTRY;
    }
}
