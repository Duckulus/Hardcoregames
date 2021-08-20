//Created by Duckulus on 01 Jul, 2021 

package de.amin.commands;

import de.amin.hardcoregames.HG;
import de.amin.mechanics.VanishManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private VanishManager vanishManager;

    public VanishCommand(){
        vanishManager = HG.INSTANCE.getVanishManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = ((Player) sender);
        if(!player.hasPermission("hg.vanish")){
            player.sendMessage("§cInsufficient permissions");
            return true;
        }

        if(vanishManager.isVanished(player)){
            vanishManager.unvanish(player);
        }else {
            vanishManager.vanish(player);
        }
        return false;
    }
}
