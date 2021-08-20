//Created by Duckulus on 16 Aug, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.LobbyState;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class BackpackerKit extends Kit implements Listener {

    private final HashMap<UUID, Inventory> backpacks;
    private final KitManager kitManager;
    private GameStateManager gameStateManager;

    public BackpackerKit(KitManager kitManager, GameStateManager gameStateManager) {
        this.kitManager = kitManager;
        this.gameStateManager = gameStateManager;
        backpacks = new HashMap<>();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(Material.CHEST).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.CHEST);
    }

    @Override
    public String getName() {
        return "Backpacker";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You have a portable chest");
        description.add("§7that gives you an additional");
        description.add("§727 Inventory Slots to store");
        description.add("§7your items.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(gameStateManager.getCurrentGameState() instanceof LobbyState) return;
        if(!(kitManager.getKit(player) instanceof BackpackerKit))return;
        if(!event.hasItem() || !event.getItem().getType().equals(Material.CHEST) || !event.getItem().getItemMeta().spigot().isUnbreakable())return;
        Inventory backpack = null;

        if(backpacks.containsKey(player.getUniqueId())){
            backpack = backpacks.get(player.getUniqueId());
        } else {
            Inventory inventory = Bukkit.createInventory(null, 3*9, "Backpack");
            backpack = inventory;
            backpacks.put(player.getUniqueId(), inventory);
        }

        player.openInventory(backpack);
        player.sendMessage("§aOpened Backpack!");
    }
}
