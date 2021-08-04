//Created by Duckulus on 04 Jul, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TankKit extends Kit implements Listener {

    private KitManager kitManager = HG.INSTANCE.getKitManager();

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.TNT);
    }

    @Override
    public String getName() {
        return "Tank";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You are immune to explosions");
        description.add("§7and Killing Entities sets off a small explosion");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onExplosionDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))return;
        Player player = (Player) e.getEntity();
        if(!(kitManager.getKit(player) instanceof TankKit))return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ||e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        if(e.getEntity().getKiller() == null)return;
        Player player = e.getEntity().getKiller();

        if(e.getEntity() instanceof Player && kitManager.getKit((Player) e.getEntity()) instanceof TankKit){
            e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(), 3f);
        }

        if(!(kitManager.getKit(player) instanceof TankKit))return;

        player.getWorld().createExplosion(e.getEntity().getLocation(), 1.5f);
    }


}
