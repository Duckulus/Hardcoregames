package de.amin.kit.impl;

import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SwitcherKit extends Kit implements Listener {
    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 16));
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.SNOW_BALL)).setDisplayName("§aSwitcher").getItem();
    }

    @Override
    public String getName() {
        return "Switcher";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Throw snowballs at your opponents");
        description.add("§7or mobs to switch places with them.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onSnowballHit(EntityDamageByEntityEvent e){
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return;
       if(!(e.getDamager() instanceof Projectile)) return;
       Projectile projectile = (Projectile) e.getDamager();
       if(!e.getDamager().getType().equals(EntityType.SNOWBALL))return;
       if(!(projectile.getShooter() instanceof Player))return;
       Player shooter = (Player) projectile.getShooter();
       if(!(HG.INSTANCE.getKitManager().getKitHashMap().get(shooter.getName()) instanceof SwitcherKit))return;
       Entity hitEntity = e.getEntity();
       Location temp = shooter.getLocation();
       shooter.teleport(hitEntity.getLocation());
       hitEntity.teleport(temp);
       shooter.sendMessage("§aYou have been switched");
       if(hitEntity instanceof Player){
           hitEntity.sendMessage("§aYou have been switched");
       }
    }

}


