package de.amin.commands;


import de.amin.hardcoregames.HG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class healCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)){return true;}
        Player p = (Player) sender;

        if(!p.hasPermission("hg.heal")){
            p.sendMessage(HG.INSTANCE.PREFIX + "§cInsufficient Permissions");
            return true;}

        if(!(args.length == 0)){
            p.sendMessage(HG.INSTANCE.PREFIX + "§7Usage§8:§9 /heal");
            return true;
        }

        p.setHealth(p.getMaxHealth());
        p.setFoodLevel(40);
        p.sendMessage(HG.INSTANCE.PREFIX + "§7You have been healed.");
        return false;
    }


}
