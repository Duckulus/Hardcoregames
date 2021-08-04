//Created by Duckulus on 12 Jul, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class KunaiKit extends Kit implements Listener {

    private HashMap<String, Arrow> kunais;

    private KitManager kitManager;

    private KitSetting cooldown = new KitSetting(this, "cooldown", 10, 0, 100);

    public KunaiKit(){
        kunais = new HashMap<>();

        kitManager = HG.INSTANCE.getKitManager();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.ARROW)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.ARROW);
    }

    @Override
    public String getName() {
        return "Kunai";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Throw an arrow and sneak \nto teleport to it");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown};
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(!(kitManager.getKit(player) instanceof KunaiKit))return;
        if(!e.hasItem() || !e.getItem().getItemMeta().spigot().isUnbreakable() ||!e.getItem().getType().equals(Material.ARROW))return;
        if(isCooldown(player, cooldown.getValue()))return;

        Arrow arrow = player.launchProjectile(Arrow.class);
        kunais.put(player.getName(), arrow);
        activateCooldown(player);
    }

    @EventHandler
    public void onCrouch(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        if(player.isSneaking())return;
        if(!(kitManager.getKit(player) instanceof KunaiKit))return;
        if(kunais.containsKey(player.getName())){
            Arrow arrow = kunais.get(player.getName());
            player.teleport(new Location(arrow.getWorld(), arrow.getLocation().getX(),
                    arrow.getLocation().getY(),
                    arrow.getLocation().getZ(),
                    player.getLocation().getYaw(),
                    player.getLocation().getPitch()));
            kunais.get(player.getName()).remove();
            player.sendMessage("§aYou have been teleported to your Kunai!");
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e){
        if(!(e.getEntity().getShooter() instanceof Player))return;
        Player player = (Player) e.getEntity().getShooter();
        kunais.remove(player.getName());
    }
}
