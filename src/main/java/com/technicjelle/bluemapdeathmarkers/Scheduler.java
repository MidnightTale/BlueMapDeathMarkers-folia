package com.technicjelle.bluemapdeathmarkers;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Scheduler {

    private static Boolean IS_FOLIA = null;

    private static boolean tryFolia() {
        try {
            Bukkit.getAsyncScheduler();
            return true;
        } catch (Throwable ignored) {
        }
        return false;
    }

    public static Boolean isFolia() {
        if (IS_FOLIA == null) IS_FOLIA = tryFolia();
        return IS_FOLIA;
    }
    public static void runAsyncSchedulerDelay(Plugin plugin, Consumer<ScheduledTask> task, int initialDelayTicks) {
        if (isFolia()) {
            Bukkit.getAsyncScheduler().runDelayed(plugin, task, initialDelayTicks, TimeUnit.MINUTES);
        } else {
            Bukkit.getScheduler().runTaskLater(plugin, () -> task.accept(null), initialDelayTicks * 20L * 60L);
        }
    }
}