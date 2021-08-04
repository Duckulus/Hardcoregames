//Created by Duckulus on 29 Jun, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class StomperKit extends Kit implements Listener {

    private final KitManager kitManager;

    private final Sound sound;

    private KitSetting horizontalRadius = new KitSetting(this, "Horizontal Radius", 3, 0, 100);
    private KitSetting verticalRadius = new KitSetting(this, "Vertical Radius", 2, 0, 100);

    public StomperKit(){
        kitManager = HG.INSTANCE.getKitManager();
        sound = Sound.ANVIL_LAND;
    }

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.DIAMOND_BOOTS);
    }

    @Override
    public String getName() {
        return "Stomper";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Jump on other Players to deal");
        description.add("§7massive damage. You take a max of");
        description.add("§72 hearts of falldamage");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{horizontalRadius, verticalRadius};
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event){
        if(!(event.getEntity() instanceof Player))return;
        Player player = (Player) event.getEntity();

        if(!event.getCause().equals(EntityDamageEvent.DamageCause.FALL))return;
        if(!(kitManager.getKitHashMap().get(player.getName()) instanceof StomperKit))return;

        double damage = event.getDamage();

        //maximum of 2 hearts falldamage
        if(event.getFinalDamage()>4){
            event.setDamage(4);
        }

        //play anvil sound for all players
        for(Player current : Bukkit.getOnlinePlayers()){
            current.playSound(player.getLocation(), sound, 2.0F, 2.0F);
        }

        //damage nearby entities if on ground and not sneaking
        for(Entity entity : player.getNearbyEntities(horizontalRadius.getValue(), verticalRadius.getValue(), horizontalRadius.getValue())) {
            if (entity instanceof LivingEntity) {
                if (entity.isOnGround()) {
                    if (!(entity == player)) {
                        if (entity instanceof Player) {
                            if (!((Player) entity).isSneaking()) {
                                ((Player) entity).damage(damage);
                            }
                        } else {
                            ((LivingEntity) entity).damage(damage);
                        }
                    }
                }
            }
        }
    }
}
