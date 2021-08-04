//Created by Duckulus on 02 Jul, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class TimelordKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final GameStateManager gameStateManager;

    private final ArrayList<String> frozenPlayers;

    private KitSetting cooldown = new KitSetting(this, "cooldown", 50, 0, 100);
    private KitSetting frozenTime = new KitSetting(this, "Frozen Time", 10, 0, 100);
    private KitSetting radius = new KitSetting(this, "radius", 7, 0, 100);

    public TimelordKit() {
        kitManager = HG.INSTANCE.getKitManager();
        gameStateManager = HG.INSTANCE.getGameStateManager();

        frozenPlayers = new ArrayList<>();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.WATCH)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.WATCH);
    }

    @Override
    public String getName() {
        return "Timelord";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Stop time for all");
        description.add("§7Players around you");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown, frozenTime, radius};
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!(kitManager.getKitHashMap().get(e.getPlayer().getName()) instanceof TimelordKit)) return;
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !e.getAction().equals(Action.RIGHT_CLICK_AIR))return;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState)){
            e.getPlayer().sendMessage("§cYou can't use this yet.");
            return;
        }
        Player player = e.getPlayer();

        if (!e.hasItem() || !e.getItem().getType().equals(Material.WATCH) || !e.getItem().getItemMeta().spigot().isUnbreakable())
            return;

        if(isCooldown(player, cooldown.getValue()))return;


        int count = 0;
        for (Entity entity : player.getNearbyEntities(radius.getValue(), radius.getValue(), radius.getValue())) {
            if (entity instanceof Player) {
                count++;
                frozenPlayers.add(entity.getName());
                entity.sendMessage("§c§lYour body stops moving...");
                entity.sendMessage("§c§lTime stops around you...");
                entity.sendMessage("§c§lIt's a Timelord!");
                Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, () -> frozenPlayers.remove(entity.getName()), (long) (frozenTime.getValue() * 20L));
            }
        }
        if(count==0){
            player.sendMessage("§cNo nearby players");
        }else{
            player.sendMessage("§eStopped time for " + count + " Players");
            spawnParticleCluster(player.getLocation());
            activateCooldown(player);
        }

    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (frozenPlayers.contains(e.getPlayer().getName())) {
            e.getPlayer().teleport(e.getPlayer().getLocation());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        frozenPlayers.remove(e.getEntity().getName());
    }

    private void spawnParticleCluster(Location location){
        for (int x = (int) -radius.getValue(); x <radius.getValue() ; x++) {
            for (int y = (int) -radius.getValue(); y <radius.getValue() ; y++) {
                for (int z = (int) -radius.getValue(); z <radius.getValue() ; z++) {
                    location.clone().getWorld().playEffect(location.clone().add(x,y,z), Effect.POTION_SWIRL, 10);
                }
            }
        }
    }
}
