package de.amin.commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

    private String prefix = "§8[§aGamemode§8]§7 ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))return true;
        Player p = (Player) sender;
        if(!p.hasPermission("hardcoregames.gm"))return true;
        if(!(args.length == 1)){
            sendUsage(p);
            return true;
        }
        switch (args[0]){
            case "0":
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(prefix + "Set Gamemode to §9Survival");
                break;
            case "1":
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(prefix + "Set Gamemode to §9Creative");
                break;
            case "2":
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(prefix + "Set Gamemode to §9Spectator");
                break;
            default:
                sendUsage(p);
        }
        return false;
    }

    public void sendUsage(Player player){
        player.sendMessage(prefix + "§cUsage: §9/gm [0/1/2]");
    }
}


