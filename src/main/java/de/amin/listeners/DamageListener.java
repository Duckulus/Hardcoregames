package de.amin.listeners;

import de.amin.gamestates.EndingState;
import de.amin.gamestates.GameState;
import de.amin.gamestates.InvincibilityState;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e){

        GameState currentGameState = HG.INSTANCE.getGameStateManager().getCurrentGameState();

        if(currentGameState instanceof LobbyState ||currentGameState instanceof EndingState){
            e.setCancelled(true);
        }
        if(currentGameState instanceof InvincibilityState){
            if(e.getEntity() instanceof Player){
                e.setCancelled(true);
            }
        }
    }


}
