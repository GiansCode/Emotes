package io.alerium.emotes.listener;

import io.alerium.emotes.util.nbt.ItemNBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public final class PlayerClickListener implements Listener {

    @EventHandler
    public void onHold(final PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = player.getInventory().getItem(event.getPreviousSlot());

        if (ItemNBT.getNBTTag(item, "emote-item").length() == 0) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(final PlayerDropItemEvent event) {
        final ItemStack item = event.getItemDrop().getItemStack();

        if (ItemNBT.getNBTTag(item, "emote-item").length() == 0) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final ItemStack item = event.getCurrentItem();

        if (ItemNBT.getNBTTag(item, "emote-item").length() == 0) {
            return;
        }

        event.setCancelled(true);
    }
}
