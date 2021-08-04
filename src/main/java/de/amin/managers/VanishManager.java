//Created by Duckulus on 01 Jul, 2021 

package de.amin.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class VanishManager {

    private ArrayList<String> vanishedPlayers;

    public VanishManager() {
        vanishedPlayers = new ArrayList<>();
    }

    public void vanish(Player player) {
        if (!vanishedPlayers.contains(player.getName())) {
            vanishedPlayers.add(player.getName());
            player.sendMessage("§eYou are now vanished");
            for (Player current : Bukkit.getOnlinePlayers()) {
                current.hidePlayer(player);
            }

        }
    }

    public void unvanish(Player player) {
        if (vanishedPlayers.contains(player.getName())) {
            vanishedPlayers.remove(player.getName());
            player.sendMessage("§eYou are no longer vanished!");
            for (Player current : Bukkit.getOnlinePlayers()) {
                current.showPlayer(player);
            }

        }
    }

    public boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getName());
    }

    public ArrayList<String> getVanishedPlayers() {
        return vanishedPlayers;
    }
}
