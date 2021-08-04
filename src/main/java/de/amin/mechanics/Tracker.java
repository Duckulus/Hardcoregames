//Created by Duckulus on 28 Jun, 2021 

package de.amin.mechanics;

import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Tracker implements Listener {

    private final AdminMode adminMode = HG.INSTANCE.getAdminMode();
    private final double minDistance = 20.0;

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(HG.INSTANCE.getAdminMode().isAdminMode(player))return;
        if (e.hasItem() && e.getItem().getType().equals(Material.COMPASS)) {
            Player target = getNearestPlayer(player);
            if (!(target == null)) {
                player.setCompassTarget(target.getLocation());
                player.sendMessage("§eTracker is now pointing at " + target.getName() + ".");
            } else {
                player.setCompassTarget(new Location(Bukkit.getWorld("world"), 0, 0, 0));
                player.sendMessage("§eNo target found. Tracker is now pointing at spawn");
            }
        }
    }

    private Player getNearestPlayer(Player player){
        double distance = Double.MAX_VALUE;
        Player target = null;
        for(Player current : Bukkit.getOnlinePlayers()){
            if(HG.INSTANCE.getPlayers().contains(target)){
                if(current!=player && current.getLocation().distance(player.getLocation())<distance
                && current.getLocation().distance(player.getLocation())>=minDistance){
                    target = current;
                }
            }
        }
        return target;
    }

}
