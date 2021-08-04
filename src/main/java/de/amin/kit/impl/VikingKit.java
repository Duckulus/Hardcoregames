//Created by Duckulus on 02 Jul, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class VikingKit extends Kit implements Listener {

    private final KitManager kitManager;

    private final double damageBoost;

    public VikingKit() {
        kitManager = HG.INSTANCE.getKitManager();
        damageBoost = 2.0;
    }

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.STONE_AXE);
    }

    @Override
    public String getName() {
        return "Viking";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You deal more damage with axes");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        Player attacker = (Player) e.getDamager();

        if (!(kitManager.getKitHashMap().get(attacker.getName()) instanceof VikingKit)) return;

        if (attacker.getItemInHand().getType().name().toLowerCase().contains("axe")) {
            e.setDamage(e.getDamage() + damageBoost);
        }


    }
}
