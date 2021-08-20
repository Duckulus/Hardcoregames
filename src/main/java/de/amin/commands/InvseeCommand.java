//Created by Duckulus on 01 Jul, 2021 

package de.amin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("hg.invsee")){
                player.sendMessage("§cInsufficient permissions");
                return true;
            }

            if(!(args.length ==1)){
                player.sendMessage("§7Usage§8: §9/invsee [Player]");
            }else {
                Player target = Bukkit.getPlayer(args[0]);
                if(!(target ==null)){
                    player.openInventory(target.getInventory());
                    player.sendMessage("§9" + target.getName() + "§7's Inventory was opened.");
                }else {
                    player.sendMessage("§cThat Player is not online.");
                }
            }
        }
        return false;
    }
}
