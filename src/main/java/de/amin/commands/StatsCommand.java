//Created by Duckulus on 05 Jul, 2021 

package de.amin.commands;

import de.amin.MySQL.Stats;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.UUID;

public class StatsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))return true;
        Player player = (Player) sender;

        if(args.length==0){
            sendStats(player, player.getUniqueId().toString());
        }else if(args.length==1){
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if(Stats.exists(target.getUniqueId().toString())){
                sendStats(player, target.getUniqueId().toString());
            }else {
                player.sendMessage("§cPlayer never joined the Server.");
            }
        }else {
            sendUsage(player);
        }

        return false;
    }

    private int getKills(String player){
        return Stats.getKills(player);
    }

    private int getDeaths(String player){
        return Stats.getDeaths(player);
    }

    private int getWins(String player){
        return Stats.getWins(player);
    }

    private int getPlayedGames(String player){
        return Stats.getPlayedGames(player);
    }

    private String getKD(String player){
        double kd;
        String formattedKD;
        if(getDeaths(player)==0){
            kd = getKills(player);
            formattedKD = String.valueOf(kd);
        }else {
            kd = ((double) getKills(player)) / getDeaths(player);
            formattedKD = new DecimalFormat("##.##").format(kd);
        }
        return formattedKD;
    }

    private String getProbability(String player){
        double probability;
        String formattedProbability = null;

        if(getWins(player)==0){
            probability = 0;
            formattedProbability = new DecimalFormat("##.##").format(probability);
        }else {
            probability = (((double)getWins(player)) / getPlayedGames(player)) * 100;
            formattedProbability = new DecimalFormat("##.##").format(probability);
        }
        return formattedProbability;
    }

    private void sendStats(Player player, String stats){
        player.sendMessage("§7§m------§r§eStats§7§m------");
        player.sendMessage("§7Player§8: §9" + Bukkit.getOfflinePlayer(UUID.fromString(stats)).getName());
        player.sendMessage("§7Kills§8: §9" + getKills(stats));
        player.sendMessage("§7Deaths§8: §9" + getDeaths(stats));
        player.sendMessage("§7K/D§8: §9" + getKD(stats));
        player.sendMessage("§7Games Played§8: §9" + getPlayedGames(stats));
        player.sendMessage("§7Wins§8: §9" + getWins(stats));
        player.sendMessage("§7Win Probability§8: §9" + getProbability(stats) + "%");
        player.sendMessage("§7§m-----------------");
    }

    private void sendUsage(Player player){
        player.sendMessage("§7Usage§8:§9 /stats <Player>");
    }
}
