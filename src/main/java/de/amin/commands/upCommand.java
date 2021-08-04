package de.amin.commands;

import de.amin.hardcoregames.HG;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class upCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player p = (Player) sender;
        Location loc = p.getLocation();
        for(int i = 256; i>0; i--){
            loc.setY(i);
            if(!loc.getBlock().getType().equals(Material.AIR)){
                loc.setY(loc.getY() + 1);
                p.teleport(loc);
                p.sendMessage(HG.INSTANCE.PREFIX + "§7You have been teleported to the surface.");
                return true;
            }
        }

        return false;
    }
}
