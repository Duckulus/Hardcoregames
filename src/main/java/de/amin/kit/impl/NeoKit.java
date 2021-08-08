//Created by Duckulus on 05 Aug, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class NeoKit extends Kit implements Listener {

    private final KitSetting dodgeProbability = new KitSetting(this, "Dodge Probability(1 in X hits)", 7, 1, 100);
    private final KitManager kitManager = HG.INSTANCE.getKitManager();

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GOLD_HELMET);
    }

    @Override
    public String getName() {
        return "Neo";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You can dodge your opponents hits");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{dodgeProbability};
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event){
        if(!(event.getEntity() instanceof Player))return;
        Player player = (Player) event.getEntity();
        if(!(kitManager.getKit(player) instanceof NeoKit))return;
        if(new Random().nextInt((int) dodgeProbability.getValue()) == 1){
            event.setCancelled(true);
            event.setDamage(0);
            Bukkit.getOnlinePlayers().forEach( e -> e.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1, 1));
        }
    }
}
