//Created by Duckulus on 01 Jul, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class FlashKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final HashMap<String, Long> cooldown;
    private final HashMap<String, Integer> distanceCooldown;

    private KitSetting baseCD = new KitSetting(this, "base cooldown", 25, 0, 100);
    private KitSetting maxDistance = new KitSetting(this, "maximum distance", 150, 0, 500);

    public FlashKit() {
        kitManager = HG.INSTANCE.getKitManager();
        cooldown = HG.INSTANCE.getCooldown();
        distanceCooldown = new HashMap();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.REDSTONE_TORCH_ON)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.REDSTONE_TORCH_ON);
    }

    @Override
    public String getName() {
        return "Flash";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Use your item to teleport to");
        description.add("§7The Location that you are ");
        description.add("§7looking at");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{baseCD, maxDistance};
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!(kitManager.getKitHashMap().get(e.getPlayer().getName()) instanceof FlashKit)) return;
        Player player = e.getPlayer();
        if (!distanceCooldown.containsKey(player.getName())) {
            distanceCooldown.put(player.getName(), 0);
        }

        if (e.hasItem() && e.getItem().getItemMeta().spigot().isUnbreakable() && e.getItem().getType().equals(Material.REDSTONE_TORCH_ON)) {

            if(isCooldown(player, distanceCooldown.get(player.getName())))return;

            Location oldLoc = player.getLocation();
            Block b = player.getTargetBlock((Set<Material>) null, (int) maxDistance.getValue());
            Location loc = b.getLocation();
            if (b.getType().equals(Material.AIR)) {
                player.sendMessage("§cNo target Block found.");
            } else {
                double distance = oldLoc.distance(loc);
                player.teleport(player.getWorld().getHighestBlockAt(b.getX(), b.getZ()).getLocation());
                player.getWorld().strikeLightningEffect(player.getLocation());

                PotionEffect speedEffect = new PotionEffect(PotionEffectType.SPEED, 60, 2, false, true);
                player.addPotionEffect(speedEffect);
                distanceCooldown.put(player.getName(), (int) (baseCD.getValue() + distance/3));
                activateCooldown(player);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(!(kitManager.getKitHashMap().get(e.getPlayer().getName()) instanceof FlashKit))return;
        if(!e.getBlock().getType().equals(Material.REDSTONE_TORCH_ON) && !e.getBlock().getType().equals(Material.REDSTONE_TORCH_OFF))return;
        e.setCancelled(true);
    }
}
