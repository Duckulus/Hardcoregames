//Created by Duckulus on 01 Jul, 2021 

package de.amin.commands;

import de.amin.hardcoregames.HG;
import de.amin.mechanics.AdminMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommand implements CommandExecutor {

    private AdminMode adminMode;

    public AdminCommand(){
        adminMode = HG.INSTANCE.getAdminMode();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("hg.adminmode")){
                player.sendMessage("§cInsufficient permissions.");
                return true;
            }
            if(adminMode.isAdminMode(player)){
                adminMode.deactivate(player);
            }else {
                adminMode.activate(player);
            }


        }
        return false;
    }
}
