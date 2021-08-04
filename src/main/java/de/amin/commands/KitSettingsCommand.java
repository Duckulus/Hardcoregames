//Created by Duckulus on 03 Aug, 2021 

package de.amin.commands;

import de.amin.Inventories.KitSettingListInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitSettingsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if(!(sender instanceof Player))return true;
        Player player = (Player) sender;
        if(!player.hasPermission("kit.settings"))return true;
        KitSettingListInventory.INVENTORY.open(player);
        return false;
    }
}
