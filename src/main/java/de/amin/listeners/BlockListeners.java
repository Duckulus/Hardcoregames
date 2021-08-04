package de.amin.listeners;

import de.amin.gamestates.*;
import de.amin.hardcoregames.HG;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListeners implements Listener {


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        GameState currentGameState = HG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof LobbyState || currentGameState instanceof EndingState){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getItemInHand().getItemMeta().spigot().isUnbreakable()){
            e.setCancelled(true);
        }
        GameState currentGameState = HG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof LobbyState){
            e.setCancelled(true);
        }
    }


}
