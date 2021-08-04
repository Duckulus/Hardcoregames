//Created by Duckulus on 04 Jul, 2021 

package de.amin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class DimensionChangeListener implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(e.getFrom().getWorld()!=e.getTo().getWorld()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPortal(PortalCreateEvent e){
        e.setCancelled(true);
    }

}
