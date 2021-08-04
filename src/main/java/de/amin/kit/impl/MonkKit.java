//Created by Duckulus on 04 Jul, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MonkKit extends Kit implements Listener {

    private KitManager kitManager;
    private GameStateManager gameStateManager;

    private KitSetting cooldown = new KitSetting(this, "cooldown", 15, 0, 100);

    public MonkKit(){
        kitManager = HG.INSTANCE.getKitManager();
        gameStateManager = HG.INSTANCE.getGameStateManager();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.BLAZE_ROD)).setUnbreakable(true).getItem());

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.BLAZE_ROD);
    }

    @Override
    public String getName() {
        return "Monk";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Swap the Item your enemy is");
        description.add("§7holding with a random one from");
        description.add(("§7his inventory"));
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onEntityRightClick(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        if(gameStateManager.getCurrentGameState() instanceof LobbyState)return;
        if(!(kitManager.getKit(player) instanceof MonkKit))return;
        if(isCooldown(player, cooldown.getValue())) return;
        if(!(e.getRightClicked() instanceof Player))return;
        if(!player.getItemInHand().getType().equals(Material.BLAZE_ROD))return;
        Player target = (Player) e.getRightClicked();
        int random = ThreadLocalRandom.current().nextInt(9, target.getInventory().getSize()-1);
        ItemStack heldItem = target.getItemInHand();
        ItemStack randomItem = target.getInventory().getItem(random);

        target.setItemInHand(randomItem);
        target.getInventory().setItem(random, heldItem);
        target.sendMessage("§aYou were monked!");
        activateCooldown(player);

    }
}
