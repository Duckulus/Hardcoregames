//Created by Duckulus on 04 Jul, 2021 

package de.amin.utils;

import de.amin.hardcoregames.HG;
import de.amin.kit.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CustomDeathMessages implements Listener {

    private KitManager kitManager;

    public CustomDeathMessages(){
        kitManager = HG.INSTANCE.getKitManager();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player dead = e.getEntity();
        Player killer = dead.getKiller();

        if(killer!=null){
            e.setDeathMessage("§b" + getNameWithKit(dead) + " was killed by " + getNameWithKit(killer) + ".");
        }else {
            e.setDeathMessage("§b" + getNameWithKit(dead) + " died.");
        }

    }

    private String getNameWithKit(Player player){
        return player.getName() + "(" + kitManager.getKit(player).getName() + ")";
    }

}
