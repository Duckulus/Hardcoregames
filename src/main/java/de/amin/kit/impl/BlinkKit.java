//Created by Duckulus on 30 Jun, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class BlinkKit extends Kit implements Listener {

    private KitSetting distance;
    private KitSetting cooldown;
    private KitSetting uses;

    private final KitManager kitManager;
    private HashMap<String, Integer> blinkUses;
    private final HashMap<String, Long> cooldownList;

    public BlinkKit(){
        cooldown = new KitSetting(this, "cooldown", 15, 0, 100);
        distance = new KitSetting(this, "distance", 10, 1, 50);
        uses = new KitSetting(this, "uses", 3, 1, 100);
        kitManager = HG.INSTANCE.getKitManager();
        blinkUses = new HashMap<>();
        cooldownList = HG.INSTANCE.getCooldown();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.NETHER_STAR)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.NETHER_STAR);
    }

    @Override
    public String getName() {
        return "Blink";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Blink away from dangerous");
        description.add("§7Situations.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown,uses,distance};
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(!(kitManager.getKitHashMap().get(p.getName()) instanceof BlinkKit))return;
        if(e.hasItem() && e.getItem().getType().equals(Material.NETHER_STAR)){

            //check for cooldown
            if(isCooldown(p, cooldown.getValue()))return;

            //check for how many uses
            if(blinkUses.containsKey(p.getName())){
                if(blinkUses.get(p.getName())<uses.getValue()){
                    blinkUses.put(p.getName(), blinkUses.get(p.getName()) + 1);
                }else {
                    blinkUses.put(p.getName(), 0);
                    activateCooldown(p);
                    return;
                }
            }else {
                blinkUses.put(p.getName(), 1);
            }

            p.teleport(p.getLocation().add(p.getLocation().getDirection().normalize().multiply(distance.getValue())));
            Block b = p.getLocation().subtract(0,1,0).getBlock();
            Material m = b.getType();
            b.setType(Material.LEAVES);
            Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
                @Override
                public void run() {
                    b.setType(m);
                }
            }, 60L);

        }
    }
}
