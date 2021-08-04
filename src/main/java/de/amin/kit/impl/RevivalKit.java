//Created by Duckulus on 10 Jul, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class RevivalKit extends Kit implements Listener {

    private final KitManager kitManager = HG.INSTANCE.getKitManager();
    private final GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();



    private final ArrayList<String> revivedPlayers;

    private KitSetting ironSwordTime = new KitSetting(this, "Seconds to Ironsword", 600, 0, 6000);
    private KitSetting diamondSwordTime = new KitSetting(this, "Seconds to Diamondsword", 1200, 0, 6000);


    public RevivalKit() {
        revivedPlayers = new ArrayList<>();
    }

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
    }

    @Override
    public String getName() {
        return "Revival";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7When you die you get a ");
        description.add("§7second chance. Only usable");
        description.add("§7once!");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{ironSwordTime, diamondSwordTime};
    }

    @EventHandler
    public void onLethalDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        Player player = (Player) e.getEntity();
        if (!(kitManager.getKit(player) instanceof RevivalKit)) return;
        if (revivedPlayers.contains(player.getName())) return;
        if (e.getFinalDamage() > player.getHealth()) {
            revivedPlayers.add(player.getName());
            e.setCancelled(true);
            player.setHealth(player.getMaxHealth());
            player.getInventory().clear();
            player.setVelocity(new Vector(0, 1, 0));
            player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
            for (int i = 0; i < 8; i++) {
                player.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
            }
            player.getInventory().setItem(0, new ItemStack(sword()));
            player.getInventory().setItem(8, new ItemStack(Material.COMPASS));
            player.sendMessage("§9§lYou were brought back to life. You will not get another chance!");
        }
    }

    private Material sword() {
        int seconds = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getSeconds();
        if (seconds < ironSwordTime.getValue()) {
            return Material.STONE_SWORD;
        } else if (seconds < diamondSwordTime.getValue()) {
            return Material.IRON_SWORD;
        } else {
            return Material.DIAMOND_SWORD;
        }
    }
}
