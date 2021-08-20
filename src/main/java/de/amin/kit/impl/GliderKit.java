//Created by Duckulus on 13 Aug, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class GliderKit extends Kit {

    private static boolean isRunning;

    public GliderKit() {
        run();

    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(Material.STICK).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.FEATHER);
    }

    @Override
    public String getName() {
        return "Glider";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Glid in the Air while holding your stick");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    private void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, () -> {
            for (Player player : HG.INSTANCE.getPlayers()) {
                if (HG.INSTANCE.getKitManager().getKit(player) instanceof GliderKit) {
                    if (player.getItemInHand().getType().equals(Material.STICK) && player.getItemInHand().getItemMeta().spigot().isUnbreakable()) {
                        if(!player.isOnGround()){
                            player.setVelocity(new Vector(
                                    player.getLocation().getDirection().getX(),
                                    -0.2,
                                    player.getLocation().getDirection().getZ()
                            ));
                        }
                    }
                }
            }
        }, 100, 1);
    }
    
}
