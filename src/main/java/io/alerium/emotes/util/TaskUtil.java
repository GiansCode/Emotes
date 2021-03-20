package io.alerium.emotes.util;

import io.alerium.emotes.EmotesPlugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public final class TaskUtil {

    private static final EmotesPlugin PLUGIN = JavaPlugin.getPlugin(EmotesPlugin.class);
    private static final BukkitScheduler SCHEDULER = PLUGIN.getServer().getScheduler();

    public static void async(final Runnable executable) {
        SCHEDULER.scheduleAsyncDelayedTask(PLUGIN, executable);
    }

    public static void queue(final Runnable executable) {
        SCHEDULER.scheduleSyncDelayedTask(PLUGIN, executable);
    }

}
