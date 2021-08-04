//Created by Duckulus on 30 Jun, 2021

package de.amin.commands;

import de.amin.Feast.Minifeast;
import de.amin.hardcoregames.HG;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TestCommand implements CommandExecutor {

    private int i;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        spawnCircle(((Player)sender).getLocation(), Integer.parseInt(args[0]));
        return false;
    }

    private void spawnCircle(Location location, int radius){
        for (int x = -radius; x <radius ; x++) {
            for (int z = -radius; z <radius ; z++) {
                Block block = location.clone().add(x,0, z).getBlock();
                if(block.getLocation().distance(location)<radius) {
                    block.setType(Material.GRASS);
                }
            }
        }
    }
}
