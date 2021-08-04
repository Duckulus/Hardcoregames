package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FiremanKit extends Kit implements Listener {

    private KitManager km;

    public FiremanKit(){
        km = HG.INSTANCE.getKitManager();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.WATER_BUCKET)).setDisplayName("§a" + getName()).getItem();
    }

    @Override
    public String getName() {
        return "Fireman";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You are immunte to fire and");
        description.add("§7you start with a water bucket.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onFireDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))return;
        Player player = (Player) e.getEntity();
        if(!(km.getKitHashMap().get(player.getName()) instanceof FiremanKit))return;
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FIRE) ||
           e.getCause().equals(EntityDamageEvent.DamageCause.FIRE_TICK) ||
           e.getCause().equals(EntityDamageEvent.DamageCause.LAVA)){
            e.setCancelled(true);
        }
    }
}


