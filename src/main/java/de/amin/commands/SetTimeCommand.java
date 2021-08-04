//Created by Duckulus on 09 Jul, 2021 

package de.amin.commands;

import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("hg.settime"))return true;
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return true;
        ((IngameState)HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().setSeconds(Integer.parseInt(args[0]));
        return false;
    }
}
