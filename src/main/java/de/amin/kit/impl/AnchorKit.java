package de.amin.kit.impl;

import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class AnchorKit extends Kit implements Listener {
    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.ANVIL)).setDisplayName("§aAnchor").getItem();
    }

    @Override
    public String getName() {
        return "Anchor";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You neither deal nor get knockback.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return;
        Entity damager = e.getDamager();
        Entity target = e.getEntity();
        HashMap<String, Kit> kits = HG.INSTANCE.getKitManager().getKitHashMap();
        if(kits.get(damager.getName()) instanceof AnchorKit || kits.get(target.getName()) instanceof AnchorKit){
            Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
                @Override
                public void run() {
                    if(e.getFinalDamage()>0){
                        target.setVelocity(new Vector(0,0,0));
                        for(Player p : HG.INSTANCE.getPlayers()){
                            p.playSound(target.getLocation(), Sound.ANVIL_LAND, 1.0F, 1.0F);
                        }
                    }
                }
            }, 1L);
        }
    }

}


