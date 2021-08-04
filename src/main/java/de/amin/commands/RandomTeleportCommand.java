package de.amin.commands;

import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import de.amin.mechanics.RandomTP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomTeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))return true;
        Player p = (Player) sender;
        String prefix = HG.INSTANCE.PREFIX;
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState)){

            p.sendMessage(prefix + "§cYou cannot use this command while the game is running.");
            return true;
        }
        if(((LobbyState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getCountdown().getSeconds() < 11){
            p.sendMessage("§cYou can't use this command at this stage");
            return true;
        }
        RandomTP.randomTeleport(p);
        return false;
    }
}
