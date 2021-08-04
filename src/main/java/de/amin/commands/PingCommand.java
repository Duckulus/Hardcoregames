package de.amin.commands;

import de.amin.hardcoregames.HG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))return true;
        Player p = (Player) sender;
        int ping = ((CraftPlayer) p).getHandle().ping;
        p.sendMessage(HG.INSTANCE.PREFIX + "§7Your Ping is §9" + ping + "ms§7.");
        return false;
    }
}
