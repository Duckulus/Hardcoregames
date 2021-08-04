//Created by Duckulus on 04 Aug, 2021 

package de.amin.mechanics;

import de.amin.hardcoregames.HG;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class SpectatorMode implements Listener {

    private final ArrayList<String> players;

    public SpectatorMode() {
        players = new ArrayList<>();
    }

    public void activate(Player player) {
        HG.INSTANCE.getPlayers().remove(player);
        player.setGameMode(GameMode.SPECTATOR);
        players.add(player.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if(players.contains(event.getPlayer().getName())){
            activate(event.getPlayer());
        }
    }

}
