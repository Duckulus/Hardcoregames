package de.amin.commands;

import de.amin.hardcoregames.HG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;

public class SetBorderCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("hg.setborder"))return true;
        FileConfiguration config = HG.INSTANCE.getFileConfig();
        if(!(args.length==1)){
            sender.sendMessage("§7Usage§8: §9/setborder [size]");
            return true;
        }
        try {
            config.set("mechanics.bordersize", Integer.parseInt(args[0]));
            config.save(HG.INSTANCE.getFile());
        } catch (NumberFormatException | IOException e) {
            sender.sendMessage("§cERROR: §7Please enter a valid number!");
            e.printStackTrace();
            return true;
        }
        sender.sendMessage(HG.INSTANCE.PREFIX + "Bordersize was changed to " + config.get("mechanics.bordersize"));
        return false;
    }
}


