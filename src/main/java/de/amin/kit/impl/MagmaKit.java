
package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class MagmaKit extends Kit implements Listener {

    private final KitManager km;

    private KitSetting duration = new KitSetting(this, "duration", 2, 1, 50);

    public MagmaKit(){
        km = HG.INSTANCE.getKitManager();
    }

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.FLINT_AND_STEEL)).setDisplayName("§a" + getName()).getItem();
    }

    @Override
    public String getName() {
        return "Magma";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Burn your enemies!");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{duration};
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player))return;
        Player damager = (Player) e.getDamager();
        Entity damaged = e.getEntity();
        if(!(km.getKitHashMap().get(damager.getName()) instanceof MagmaKit))return;
        int random = new Random().nextInt(100);
        if (random>90){
            damaged.setFireTicks((int) (20*duration.getValue()));
        }
    }


}
