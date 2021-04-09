package io.alerium.emotes.listener.impl;

import io.alerium.emotes.util.nbt.ItemNBT;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public final class PlayerClickListener implements Listener {

    private static final int HEAD_INVENTORY_SLOT = 39;

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
    public void onSwap(final PlayerSwapHandItemsEvent event) {
        final ItemStack item = event.getMainHandItem();

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

        final int slot = event.getSlot();
        if (slot == HEAD_INVENTORY_SLOT) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();
        final Inventory inventory = player.getInventory();

        inventory.setItem(event.getSlot(), null);
        player.updateInventory();
    }
}
