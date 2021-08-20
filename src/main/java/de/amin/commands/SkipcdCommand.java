//Created by Duckulus on 01 Jul, 2021 

package de.amin.commands;

import de.amin.hardcoregames.HG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkipcdCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("hg.skipcd")){
                player.sendMessage("§cInsufficient Permissions.");
                return true;
            }
            HG.INSTANCE.getCooldown().remove(player.getName());
        }
        return false;
    }
}
