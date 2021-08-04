//Created by Duckulus on 11 Jul, 2021 

package de.amin.mechanics;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;

public class Grenade implements Listener {

    @EventHandler
    public void onEggHit(ProjectileHitEvent e){
        if(!(e.getEntity() instanceof Egg))return;
        Location entLocation = e.getEntity().getLocation();
        entLocation.getWorld().playEffect(entLocation, Effect.EXPLOSION_HUGE, 20);
        for(Entity ent : e.getEntity().getNearbyEntities(10, 10, 10)){
            Vector launchDirection = ent.getLocation().toVector().add(entLocation.toVector().multiply(-1));
            launchDirection.setY(1.5);
            ent.setVelocity(launchDirection);
        }

    }

}
