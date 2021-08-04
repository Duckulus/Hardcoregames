package de.amin.mechanics;

import de.amin.hardcoregames.HG;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class WorldBorder implements Listener {

    public static int size = (int) HG.INSTANCE.getFileConfig().get("mechanics.bordersize");

    @EventHandler
    public void onMove(PlayerMoveEvent e){
        Player p = e.getPlayer();
        Location pos = p.getLocation();
        int bordersize = (int) HG.INSTANCE.getFileConfig().get("mechanics.bordersize");
        double yThrow = 0.5;
        if(pos.getX()>bordersize){
            p.setVelocity(new Vector(-1, yThrow, 0));
            p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "ATTENTION!" + " §9You reached the World Border");
        }
        if(pos.getX()<bordersize * -1){
            p.setVelocity(new Vector(1, yThrow, 0));
            p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "ATTENTION!" + " §9You reached the World Border");
        }
        if(pos.getZ()>bordersize){
            p.setVelocity(new Vector(0, yThrow, -1));
            p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "ATTENTION!" + " §9You reached the World Border"); 
        }
        if(pos.getZ()<bordersize * -1){
            p.setVelocity(new Vector(0, yThrow, 1));
            p.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + "ATTENTION!" + " §9You reached the World Border");
        }
    }
}


