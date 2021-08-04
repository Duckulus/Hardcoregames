//Created by Duckulus on 27 Jun, 2021 

package de.amin.commands;

import de.amin.Feast.Feast;
import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ForceFeastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();
        if(!sender.hasPermission("feast.force"))return false;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return false;
        if(Feast.isAnnounced()){
            sender.sendMessage("§cFeast has already been announced");
        }else {
            Feast feast = new Feast();
            ((IngameState) gameStateManager.getCurrentGameState()).getTimer().setFeast(feast);
        }
        return false;
    }
}
