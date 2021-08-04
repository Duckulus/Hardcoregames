package de.amin.mechanics;

import de.amin.hardcoregames.HG;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Launcher implements Listener {

    private HashMap<String, Long> launchedPlayers = HG.INSTANCE.getLaunchedPlayers();
    private final Material[] materialArray = new Material[]{Material.WOOD_PLATE, Material.GOLD_PLATE, Material.IRON_PLATE, Material.STONE_PLATE, Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.SNOW};
    private ArrayList<Material> materials = new ArrayList<>();
    private double factor = (double) HG.INSTANCE.getConfig().get("mechanics.launchfactor");


    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location loc = p.getLocation();
        loc.setY(loc.getY()-1);

        if(loc.getBlock().getType().equals(Material.SPONGE)){
            Collections.addAll(materials, materialArray);
            Location aboveLauncher = loc;
            aboveLauncher.setY(loc.getY()+1);

            Location belowLauncher = loc;
            belowLauncher.setY(belowLauncher.getY()-2);
            boolean running = true;
            int boost = 0;
            while (running){
                if(belowLauncher.getBlock().getType().equals(Material.SPONGE)){
                    boost++;
                    belowLauncher.setY(belowLauncher.getY()-1);
                }else {
                    running=false;
                }
            }


            p.setVelocity(new Vector(0.0, factor*Math.pow(1.5, boost), 0.0));
            if(!materials.contains(p.getLocation().getBlock().getType())) {
                launchedPlayers.put(p.getName(), System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!e.getEntityType().equals(EntityType.PLAYER))return;
        Player p = (Player) e.getEntity();
        if(!e.getCause().equals(EntityDamageEvent.DamageCause.FALL))return;
        if(!launchedPlayers.containsKey(p.getName()))return;

        long be = launchedPlayers.get(p.getName());
        int rest = (int) ((be + (5*1000)) - System.currentTimeMillis());
        if(rest>0){
            e.setCancelled(true);
            launchedPlayers.remove(p.getName());
        }
    }
}


