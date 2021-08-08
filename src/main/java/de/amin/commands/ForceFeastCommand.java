//Created by Duckulus on 27 Jun, 2021 

package de.amin.commands;

import de.amin.feast.Feast;
import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceFeastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();
        if(!sender.hasPermission("feast.force"))return false;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return false;
        Player player = (Player) sender;
        if(Feast.isFeast()){
            player.sendMessage("§cFeast has already spawned!");
        }else if(!Feast.isAnnounced()){
            Feast feast = new Feast();
            Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "§7Feast has been forced");
            gameStateManager.getIngameState().getTimer().setFeast(feast);
        }else {
            Feast.isFeast = true;
            Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "§7Feast spawn has been forced by an Operator");
            gameStateManager.getIngameState().getTimer().getFeast().spawnChests();
        }
        return false;
    }
}
