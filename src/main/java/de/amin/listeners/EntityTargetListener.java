package de.amin.listeners;

import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;

public class EntityTargetListener implements Listener {

    @EventHandler
    public void onTarget(EntityTargetLivingEntityEvent evt){
        if((HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return;
        if (evt.getTarget() instanceof Player) {
            evt.setCancelled(true);
            evt.setTarget(null);
        }
        if (evt.getEntity() instanceof Monster) {
            Monster mob = (Monster) evt.getEntity();
            if (mob.getTarget() instanceof Player) {
                mob.setTarget(null);
            }
        }
    }
    }



