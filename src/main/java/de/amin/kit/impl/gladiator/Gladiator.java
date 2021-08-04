//Created by Duckulus on 04 Jul, 2021 

package de.amin.kit.impl.gladiator;

import de.amin.hardcoregames.HG;
import de.amin.kit.KitSetting;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class Gladiator implements Listener {

    private static final ArrayList<Gladiator> gladiators = new ArrayList<>();

    private final ArrayList<String> players = new ArrayList<>();
    private final ArrayList<Block> gladiatorBlocks = new ArrayList<>();

    private final Player player1;
    private final Player player2;
    private final Location player1Start;
    private final Location player2Start;
    private final Location origin;


    private int taskID;
    private int buildID;
    private boolean end;
    private int status;

    public Gladiator(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1Start = player1.getLocation();
        player2Start = player2.getLocation();
        gladiators.add(this);
        origin = player1.getWorld().getHighestBlockAt(player1.getLocation()).getLocation().add(0, 20, 0);
        status = 0;

        end = false;

        players.add(player1.getName());
        players.add(player2.getName());

        spawn();
        run();
    }

    public void spawn() {
        for (int i = 0; i < (int) KitSetting.get(new GladiatorKit(), "height"); i+=1) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, () -> {
                status++;
                if(status == 1 ||status == (int) KitSetting.get(new GladiatorKit() , "height") - 1){
                    buildPlane(status);
                    if(status==1){
                        teleport();
                    }
                }else if(status < (int) KitSetting.get(new GladiatorKit(), "height")){
                    buildBorder(status);
                }
            }, i);
        }
    }

    public void teleport() {
        Location location1 = origin.clone().add(2, 2, 2);
        location1.setYaw(-45f);
        location1.setPitch(0);

        Location location2 = origin.clone().add(KitSetting.get(new GladiatorKit(), "width") - 2, 2, KitSetting.get(new GladiatorKit(), "length") - 2);
        location2.setYaw(135f);
        location2.setPitch(0);

        player1.teleport(location1);
        player2.teleport(location2);
    }

    public void end() {

        if (end) return;

        for (Block b : gladiatorBlocks) {
            b.setType(Material.AIR);
        }

        player1.teleport(player1Start);
        player2.teleport(player2Start);

        players.remove(player1.getName());
        players.remove(player2.getName());

        Bukkit.getScheduler().cancelTask(taskID);
        end = true;
        gladiators.remove(this);
    }

    private void run() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                Location loc1 = player1.getLocation();
                Location loc2 = player2.getLocation();
                Location point1 = origin.clone();
                Location point2 = origin.clone().add(KitSetting.get(new GladiatorKit(), "width"), KitSetting.get(new GladiatorKit(), "height"), KitSetting.get(new GladiatorKit(), "length"));

                int xMin = (int) point1.getX();
                int xMax = (int) point2.getX();
                int yMin = (int) point1.getY();
                int yMax = (int) point2.getY();
                int zMin = (int) point1.getZ();
                int zMax = (int) point2.getZ();

                if (!(loc1.getX() > xMin) || !(loc1.getX() < xMax) || !(loc1.getY() > yMin) || !(loc1.getY() < yMax) || !(loc1.getZ() > zMin) || !(loc1.getZ() < zMax)
                        || !(loc2.getX() > xMin) || !(loc2.getX() < xMax) || !(loc2.getY() > yMin) || !(loc2.getY() < yMax) || !(loc2.getZ() > zMin) || !(loc2.getZ() < zMax)) {
                    end();
                }
            }
        }, 0L, 5L);
    }

    public static boolean isInFight(Player player) {
        for (Gladiator g : gladiators) {
            if (g.getPlayers().contains(player.getName())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getPlayers() {
        return players;
    }

    public ArrayList<Block> getGladiatorBlocks() {
        return gladiatorBlocks;
    }

    public static ArrayList<Gladiator> getGladiators() {
        return gladiators;
    }

    private void buildPlane(int y) {
        for (int x = 0; x < KitSetting.get(new GladiatorKit(), "width"); x++) {
            for (int z = 0; z < KitSetting.get(new GladiatorKit(), "length"); z++) {
                Block b = origin.clone().add(x, y, z).getBlock();
                b.setType(Material.STAINED_GLASS);
                gladiatorBlocks.add(b);
                b.getWorld().playEffect(b.getLocation(), Effect.COLOURED_DUST, 1);
                for(String s : getPlayers()){
                    Bukkit.getPlayer(s).playSound(Bukkit.getPlayer(s).getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
                }
            }
        }
    }

    private void buildBorder(int y){
        for (int x = 0; x < KitSetting.get(new GladiatorKit(), "width"); x++) {
            for (int z = 0; z < KitSetting.get(new GladiatorKit(), "length"); z++) {
                Block b = origin.clone().add(x, y, z).getBlock();
                b.setType(Material.STAINED_GLASS);
                gladiatorBlocks.add(b);
                b.getWorld().playEffect(b.getLocation(), Effect.COLOURED_DUST, 1);
                for(String s : getPlayers()){
                    Bukkit.getPlayer(s).playSound(Bukkit.getPlayer(s).getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1);
                }
            }
        }

        for (int x = 1; x < KitSetting.get(new GladiatorKit(), "width")-1; x++) {
            for (int z = 1; z < KitSetting.get(new GladiatorKit(), "length")-1; z++) {
                Block b = origin.clone().add(x, y, z).getBlock();
                b.setType(Material.AIR);
            }
        }
    }
}
