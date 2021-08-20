//Created by Duckulus on 16 Aug, 2021 

package de.amin.commands;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.mechanics.Pit;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForcePitCommand implements CommandExecutor {

    private final HG plugin;
    private final GameStateManager gameStateManager;

    public ForcePitCommand(HG plugin) {
        this.plugin = plugin;
        this.gameStateManager = plugin.getGameStateManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("hg.forcepit"))return true;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState)){
            sender.sendMessage("§cThis command can't be used at this stage of the game!");
            return true;
        }

        if(!Pit.isPitAnnounced) {
            Pit pit = new Pit();
            gameStateManager.getIngameState().getTimer().setPit(pit);
            Bukkit.broadcastMessage(plugin.PREFIX + "§7Pit announcement has been forced!");
        }else if(!Pit.isPit) {
            Pit.isPit = true;
            Bukkit.broadcastMessage(plugin.PREFIX + "§7Pit has been forced!");
        }else {
            sender.sendMessage("§cPit has already spawned!");
        }
        return false;
    }
}
