package de.amin.commands;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceStartCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = HG.INSTANCE.PREFIX;
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(p.hasPermission("hg.forcestart")){
                if(args.length == 0){
                    GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();
                    if(gameStateManager.getCurrentGameState() instanceof LobbyState){
                        LobbyState lobbyState = (LobbyState) gameStateManager.getCurrentGameState();
                        if(lobbyState.getCountdown().isRunning()) {
                                lobbyState.getCountdown().setSeconds(15);
                            Bukkit.broadcastMessage(prefix + "§aThe game has been force-started by a player");
                        }else{
                            p.sendMessage(prefix + "§cNot enough Players");
                        }
                    }else{
                        p.sendMessage(prefix + "§cThis Command cannot be used after the game has already started");
                    }
                }else{
                    p.sendMessage(prefix + "§7Usage§8: §9/fs");
                }
            }else{
                p.sendMessage(prefix + "§cInsufficient Permissions");
            }
        }
        return false;
    }
}
