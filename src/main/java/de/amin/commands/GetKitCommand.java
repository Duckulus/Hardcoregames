package de.amin.commands;

import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetKitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String prefix = HG.INSTANCE.PREFIX;
        if(!(sender instanceof Player))return true;
        Player player = (Player) sender;
        if(args.length==0) {
            String playerKitName = HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName();
            player.sendMessage( prefix + "§7Your Kit: §a" + playerKitName);
        }else if(args.length==1){
            if(!player.hasPermission("hardcoregames.seekits")){
                player.sendMessage(prefix + "Insufficient Permissions");
                return true;
            }
            if(args[0].equals("all")){
                player.sendMessage("§7All Kits§8:");
                for(Player current : HG.INSTANCE.getPlayers()){
                    player.sendMessage("§e" + current.getName() + "§8:§a " + HG.INSTANCE.getKitManager().getKitHashMap().get(current.getName()).getName());
                }
            }else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    player.sendMessage(prefix + "§cThat Player is not Online at the moment.");
                    return true;
                }
                String targetKitName = HG.INSTANCE.getKitManager().getKitHashMap().get(target.getName()).getName();
                player.sendMessage(prefix + "§e" + target.getName() + "'s §7Kit: §a" + targetKitName);
            }
        }else{
            player.sendMessage("§7Usage§8:§9 /getkit <Player/all>");
        }
        return false;
    }
}
