package net.kunmc.lab.distancescore;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class DistanceScore extends JavaPlugin implements Listener {

    private Objective objective;

    @Override
    public void onEnable() {
        // Plugin startup logic

        Scoreboard sc = getServer().getScoreboardManager().getMainScoreboard();
        objective = sc.getObjective("distance");
        if (objective == null)
            objective = sc.registerNewObjective("distance", "dummy", "最大距離");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerMoved(PlayerMoveEvent event) {
        Location spawnLocation = event.getPlayer().getWorld().getSpawnLocation();
        int distance = (int) Math.floor(event.getTo().distance(spawnLocation));

        Score score = objective.getScore(event.getPlayer().getName());
        int maxDistance = score.isScoreSet() ? score.getScore() : 0;
        if (maxDistance < distance)
            score.setScore(distance);
    }

}
