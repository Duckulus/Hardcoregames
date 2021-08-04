//Created by Duckulus on 29 Jun, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
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

public class AchillesKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final GameStateManager gameStateManager;
    private final double woodToolDamageBoost;

    public AchillesKit(){
        kitManager = HG.INSTANCE.getKitManager();
        gameStateManager = HG.INSTANCE.getGameStateManager();
        woodToolDamageBoost = 2.0D;
    }

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.WOOD_SWORD);
    }

    @Override
    public String getName() {
        return "Achilles";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Wooden weapons deal a lot of damage to you,");
        description.add("§7everything else is harmless.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e){
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        if(e.getEntity() instanceof Player && e.getDamager() instanceof Player){
            Player attacker = (Player) e.getDamager();
            Player attacked = (Player) e.getEntity();
            if(!(kitManager.getKitHashMap().get(attacked.getName()) instanceof AchillesKit))return;

            if(attacker.getItemInHand().getType().equals(Material.WOOD_SWORD)
            || attacker.getItemInHand().getType().equals(Material.WOOD_AXE)){
                e.setDamage(e.getDamage()*woodToolDamageBoost);
            }else {
                attacker.sendMessage("§cThis Player is an Achilles. You don't deal any damage to him.");
                e.setDamage(e.getDamage()/2);
            }
        }
    }
}
