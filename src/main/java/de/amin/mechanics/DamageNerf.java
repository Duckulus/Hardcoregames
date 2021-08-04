//Created by Duckulus on 30 Jun, 2021 

package de.amin.mechanics;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageNerf implements Listener {

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if(!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))return;
        event.setDamage(event.getFinalDamage()*0.75);
    }



}
