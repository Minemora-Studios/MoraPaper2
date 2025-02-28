package net.minemora;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MoraPaper {

    public static void startNearestPlayerUpdater(ServerLevel level) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            List<ServerPlayer> playersSnapshot = new ArrayList<>(level.players());
            for (Entity entity : level.getEntitiesAsync()) {
                if (entity instanceof Mob mob) {
                    updateNearestPlayerForMob(mob, playersSnapshot);
                }
            }
        }, 0, 100, TimeUnit.MILLISECONDS);
    }

    private static void updateNearestPlayerForMob(Mob mob, List<ServerPlayer> players) {
        double minDistance = Double.MAX_VALUE;
        Player player = null;

        for (Player player1 : players) {
            if (EntitySelector.PLAYER_AFFECTS_SPAWNING.test(player1)) {
                double distance = player1.distanceToSqr(mob.getX(), mob.getY(), mob.getZ());
                if (distance < minDistance) {
                    minDistance = distance;
                    player = player1;
                }
            }
        }

        mob.nearestPlayerUUID = player != null ? player.gameProfile.getId() : null;
        mob.nearestPlayerDistanceSquared = minDistance;
    }
}
