//Created by Duckulus on 08 Jul, 2021 

package de.amin.mechanics;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.function.pattern.BlockPattern;
import com.sk89q.worldedit.internal.LocalWorldAdapter;
import com.sk89q.worldedit.patterns.Pattern;
import de.amin.hardcoregames.HG;
import de.amin.kit.impl.gladiator.Gladiator;
import org.bukkit.*;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Pit {

    public static boolean isPitAnnounced = false;
    public static boolean isPit = false;

    private int radius;
    private int taskID;
    private int seconds;
    private HG hg;

    public Pit() {
        radius = HG.INSTANCE.getFileConfig().getInt("pit.radius");
        taskID = 0;
        seconds = HG.INSTANCE.getFileConfig().getInt("pit.countdown");
        hg = HG.INSTANCE;
        runPit();
        runDamage();
    }

    public void runPit() {
        isPitAnnounced = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, () -> {

            if(isPit) {
                Bukkit.getScheduler().cancelTask(taskID);
                Bukkit.broadcastMessage(hg.PREFIX + "§7Pit Deathmatch is starting!");
                for(Gladiator g : Gladiator.getGladiators()){
                    g.end();
                }
                spawnPit();
                teleportPlayers();
                removeEntities();
            }

            switch (seconds) {
                case 5 * 60:
                case 4 * 60:
                case 3 * 60:
                case 2 * 60:
                case 60:
                case 30:
                case 15:
                case 10:
                case 5:
                case 4:
                case 3:
                case 2:
                case 1:
                    Bukkit.broadcastMessage(hg.PREFIX + "§7Pit Deathmatch will start in " + Scoreboards.formatTime(seconds) + ".");
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.playSound(player.getLocation(), Sound.NOTE_BASS, 1, 1);
                    }
                    break;
                case 0:
                    Bukkit.getScheduler().cancelTask(taskID);
                    Bukkit.broadcastMessage(hg.PREFIX + "§7Pit Deathmatch is starting!");
                    for(Gladiator g : Gladiator.getGladiators()){
                        g.end();
                    }
                    isPit = true;
                    spawnPit();
                    teleportPlayers();
                    removeEntities();
            }
            seconds--;
        }, 0, 20);
    }

    private void removeEntities(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for(Entity e : Bukkit.getWorld("world").getEntities()){
                    if(!(e instanceof Player)){
                        e.remove();
                    }
                }
            }
        }, 20);
    }

    private void runDamage(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, () -> {
            if(isPit){
                for (Player player : HG.INSTANCE.getPlayers()){
                    if(player.getLocation().getY()>40
                    || player.getLocation().getX()>radius+10
                    || player.getLocation().getX()<(-radius) -10
                    || player.getLocation().getZ()>radius+10
                    || player.getLocation().getZ()<(-radius) -10){
                        player.sendMessage("§9§lATTENTION! §r§cMove closer to the center!");
                        player.damage(2.0);
                    }
                }
            }
        }, 0, 40);
    }

    private void teleportPlayers() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.teleport(getRandomPitLocation());
                }
            }
        }, 20L);

    }

    private Location getRandomPitLocation(){
        World world = Bukkit.getWorld("world");
        Location location = new Location(world, 0, 0, 0);
        location.setY(16);
        location.setX(ThreadLocalRandom.current().nextInt(-radius/2, radius/2));
        location.setZ(ThreadLocalRandom.current().nextInt(-radius/2, radius/2));

        if (location.distance(new Location(world, 0, 16, 0)) > radius){
            return getRandomPitLocation();
        }else {
            return location;
        }
    }

    private void spawnPit() {
        createCylinder();
    }


    private void createCylinder() {
        for (int i = 15; i < 200 ; i++) {
            spawnCircle(new Location(Bukkit.getWorld("world"), 0, i, 0), radius, Material.AIR);
        }
        spawnCircle(new Location(Bukkit.getWorld("world"), 0, 15, 0), radius, Material.STONE);
    }

    public void spawnCircle(Location loc, int radius, Material m) {
        for (int i = -radius; i <radius ; i++) {
            for (int j = -radius; j <radius ; j++) {
                Block b = loc.clone().add(i,0,j).getBlock();
                if(b.getLocation().distance(loc)<radius){
                    b.setType(m);
                }
            }
        }
    }

    public int getSeconds() {
        return seconds;
    }
}



