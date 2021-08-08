//Created by Duckulus on 28 Jun, 2021 

package de.amin.commands;

import de.amin.feast.Feast;
import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeastCommand implements CommandExecutor {

    private GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player))return true;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return true;
        Player player = (Player) sender;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState)){
            player.sendMessage("§cThat command can't be used at this stage");
        }
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();
        if(Feast.isAnnounced()){
            player.setCompassTarget(new Location(Bukkit.getWorld("world"), feast.getxPos(), feast.getyPos(), feast.getzPos()));
            player.sendMessage("§eTracker is now pointing at feast.");
        }else {
            player.sendMessage("§eFeasts location is not known yet.");
        }
        return false;
    }
}
