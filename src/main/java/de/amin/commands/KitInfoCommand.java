package de.amin.commands;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitInfoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(args.length==0){
            if(!(sender instanceof Player))return true;
            Player p = (Player) sender;
            Kit playerKit = HG.INSTANCE.getKitManager().getKitHashMap().get(p.getName());
            p.sendMessage("");
            p.sendMessage("§7Kitinfo for: §8" + playerKit.getName());
            for(int i = 0; i<playerKit.getDescription().size(); i++){
                p.sendMessage("§a" + ChatColor.stripColor(playerKit.getDescription().get(i)));
            }
        }
        return false;
    }
}


