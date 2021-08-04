package de.amin.listeners;

import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class DropPickupListeners implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState || e.getItemDrop().getItemStack().getItemMeta().spigot().isUnbreakable()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e){
        if(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState){
            e.setCancelled(true);
        }
    }
}
