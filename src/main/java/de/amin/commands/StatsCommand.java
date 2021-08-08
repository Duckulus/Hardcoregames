//Created by Duckulus on 07 Aug, 2021 

package de.amin.commands;

import de.amin.hardcoregames.HG;
import de.amin.stats.StatsGetter;
import de.amin.utils.UUIDFetcher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.DecimalFormat;
import java.util.UUID;

public class StatsCommand implements CommandExecutor {

    private StatsGetter stats;

    public StatsCommand(StatsGetter stats) {
        this.stats = stats;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                if((args.length > 1)){
                    sender.sendMessage("§7Usage§8:§9 /stats [Player]");
                    return;
                }
                String target;

                if(args.length==0){
                    target = sender.getName();
                }else {
                    target = args[0];
                }

                UUID targetUUID = UUIDFetcher.getUUID(target);

                if(!stats.exists(target)){
                    sender.sendMessage("§cThat Player never joined the Server!");
                    return;
                }

                double kills = stats.get(target, "KILLS");
                double deaths = stats.get(target, "DEATHS");
                double kd = 0;

                if(deaths == 0){
                    kd = kills;
                }else {
                    kd = kills/deaths;
                }

                double gamesPlayed = stats.get(target, "GAMESPLAYED");
                double wins = stats.get(target, "WINS");

                double probability = 0;
                if(gamesPlayed!=0){
                    probability = wins/gamesPlayed * 100;
                }

                DecimalFormat noDecimal = new DecimalFormat("##");
                DecimalFormat twoDecumal = new DecimalFormat("##.##");

                sender.sendMessage("§9=====[§bStats§9]=====");
                sender.sendMessage("§7Name§8: §6" + UUIDFetcher.getName(targetUUID));
                sender.sendMessage("§7Kills§8: §6" + noDecimal.format(kills));
                sender.sendMessage("§7Deaths§8: §6" + noDecimal.format(deaths));
                sender.sendMessage("§7K/D§8: §6" + twoDecumal.format(kd));
                sender.sendMessage("§7Games Played§8: §6" + noDecimal.format(gamesPlayed));
                sender.sendMessage("§7Wins§8: §6" + noDecimal.format(wins));
                sender.sendMessage("§7Win Probability§8: §6" + twoDecumal.format(probability) + "%");
                sender.sendMessage("§9================");
            }
        });

        return false;
    }
}
