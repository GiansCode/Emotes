package io.alerium.emotes.object.impl;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.util.TextureUtil;
import io.alerium.emotes.util.time.TimeAPI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class Emote {

    private static final EmotesPlugin PLUGIN = JavaPlugin.getPlugin(EmotesPlugin.class);

    private final String identifier;
    private final TimeAPI.TimeResult timeResult;
    private final LinkedList<String> textureSequence;

    public Emote(final String identifier, final TimeAPI.TimeResult updateRate, final LinkedList<String> textureSequence) {
        this.identifier = identifier;
        this.timeResult = updateRate;
        this.textureSequence = textureSequence;
    }

    public void send(final Player player) {
        final ItemStack headItem = player.getInventory().getItem(EquipmentSlot.HEAD);
        PLUGIN.getEmoteRegisterable().getItemRegistry().put(player.getUniqueId(), headItem);

        final AtomicInteger executions = new AtomicInteger(this.textureSequence.size() - 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    player.getInventory().setItem(EquipmentSlot.HEAD, headItem);
                    cancel();
                    return;
                }

                final int execution = executions.getAndDecrement();
                if (execution < 0) {
                    player.getInventory().setItem(EquipmentSlot.HEAD, headItem);
                    cancel();
                    return;
                }

                player.getInventory().setItem(EquipmentSlot.HEAD, TextureUtil.getTexturedHead(textureSequence.get(execution)));
            }
        }.runTaskTimer(PLUGIN, 0, this.timeResult.to(TimeUnit.SECONDS) * 20);
    }

    public String getIdentifier() {
        return identifier;
    }

    public List<String> getTextureSequence() {
        return textureSequence;
    }

    public TimeAPI.TimeResult getTimeResult() {
        return timeResult;
    }
}
