package de.amin.commands;

import de.amin.gamestates.GameState;
import de.amin.hardcoregames.HG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class EndGameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender.hasPermission("hardcoregames.endgame")){
            HG.INSTANCE.getGameStateManager().setGameState(GameState.ENDING_STATE);
        }
        return false;
    }
}


