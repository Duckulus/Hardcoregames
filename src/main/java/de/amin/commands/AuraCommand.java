//Created by Duckulus on 10 Jul, 2021 

package de.amin.commands;

import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class AuraCommand implements CommandExecutor{

    public ArrayList<Player> auraPlayers;

    public AuraCommand(){
        auraPlayers = new ArrayList<>();
        run();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("hg.aura"))return true;
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(auraPlayers.contains(player)){
                auraPlayers.remove(player);
            }else {
                auraPlayers.add(player);
            }
        }
        return false;
    }

    private void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE , new Runnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(auraPlayers.contains(p)) {
                        Location location = p.getLocation();
                        for (int degree = 0; degree < 360; degree++) {
                            double radians = Math.toRadians(degree);
                            double x = Math.cos(radians);
                            double z = Math.sin(radians);
                            location.getWorld().playEffect(location.clone().add(x,0,z), Effect.COLOURED_DUST, 1);
                        }
                    }
                }
            }
        }, 20, 1);
    }




}
