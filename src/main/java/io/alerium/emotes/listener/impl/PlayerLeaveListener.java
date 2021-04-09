package io.alerium.emotes.listener.impl;

import io.alerium.emotes.EmotesPlugin;
import io.alerium.emotes.util.nbt.ItemNBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public final class PlayerLeaveListener implements Listener {

    private final EmotesPlugin plugin;

    public PlayerLeaveListener(final EmotesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLeave(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory().getItem(EquipmentSlot.HEAD);

        if (ItemNBT.getNBTTag(item, "emote-item").length() == 0) {
            return;
        }

        player.getInventory().setItem(EquipmentSlot.HEAD, plugin.getEmoteRegisterable().getItemRegistry().get(player.getUniqueId()));
    }
}
