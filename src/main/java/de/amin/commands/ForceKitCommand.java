//Created by Duckulus on 07 Jul, 2021 

package de.amin.commands;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.LobbyState;
import de.amin.kit.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceKitCommand implements CommandExecutor {

    private GameStateManager gameStateManager;
    private KitManager kitManager;

    public ForceKitCommand(GameStateManager gameStateManager, KitManager kitManager) {
        this.gameStateManager = gameStateManager;
        this.kitManager = kitManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (!player.hasPermission("hg.forcekit")) return true;
        if (!(gameStateManager.getCurrentGameState() instanceof LobbyState)) {
            player.sendMessage("§cThis command cannot be used at this stage of the game.");
            return true;
        }

        if (!(args.length == 1)) {
            player.sendMessage("§7Usage§8:§9 /forcekit [Kit/Reset]");
            return true;
        }

        if(args[0].equalsIgnoreCase("reset")){
            kitManager.setForcedKit(null);
            return true;
        }

        if (kitManager.getKitByName(args[0]) == null) {
            player.sendMessage("§cKit not found");
            return true;
        }

        Bukkit.broadcastMessage("§bA kit has been forced by an Operator");
        kitManager.forceKit(kitManager.getKitByName(args[0]));
        return false;
    }
}
