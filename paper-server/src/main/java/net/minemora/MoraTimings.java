package net.minemora;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class MoraTimings {

    static class TotalTiming {
        public long total = 0;
        public int count = 0;
    }

    private static final Map<String, Long> timings = new HashMap<>();
    private static final Map<String, TotalTiming> accumulated = new HashMap<>();

    private static boolean loaded = false;

    public static void load() {
        if (loaded) return;

        loaded = true;

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(MoraTimings::printAccumulated,
            30, 30, TimeUnit.SECONDS);
    }

    public static void start(String name) {
        timings.put(name + "-start", System.nanoTime());
    }

    public static void end(String name) {
        timings.put(name + "-end", System.nanoTime());

        long diff = get(name);
        accumulated.compute(name, (k, v) -> {
            if (v == null) {
                v = new TotalTiming();
            }

            v.total += diff;
            v.count++;

            return v;
        });
    }

    public static long get(String name) {
        return timings.get(name + "-end") - timings.get(name + "-start");
    }

    public static void log(String message) {
        Bukkit.getLogger().log(Level.INFO, "[Timings] " + message);
    }

    public static void printAccumulated() {
        log("Accumulated:");

        for (Map.Entry<String, TotalTiming> entry : accumulated.entrySet()) {
            final TotalTiming timing = entry.getValue();

            float average = ((float) nanoToMillis(timing.total) / timing.count);
            log(entry.getKey() + ": " + nanoToMillis(timing.total) + "ms (AVG: " + average + "ms, COUNT: " + timing.count + ")");
        }
    }

    private static long nanoToMicro(long nano) {
        return nano / 1000;
    }

    private static long microToMillis(long micro) {
        return micro / 1000;
    }

    private static long nanoToMillis(long nano) {
        return microToMillis(nanoToMicro(nano));
    }

}
