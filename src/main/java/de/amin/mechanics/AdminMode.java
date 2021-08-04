//Created by Duckulus on 01 Jul, 2021 

package de.amin.mechanics;

import de.amin.gamestates.GameState;
import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.gamestates.InvincibilityState;
import de.amin.hardcoregames.HG;
import de.amin.managers.VanishManager;
import de.amin.utils.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;

public class AdminMode{

    private final HashMap<String, ItemStack[]> adminPlayers;
    private final VanishManager vanishManager;
    private final GameStateManager gameStateManager;

    private final ItemStack players;
    private final ItemStack playerInfo;
    private final ItemStack invSee;
    private final ItemStack vanishOn;
    private final ItemStack vanishOff;
    private final ItemStack teleport;

    public AdminMode() {
        adminPlayers = new HashMap<>();
        vanishManager = HG.INSTANCE.getVanishManager();
        gameStateManager = HG.INSTANCE.getGameStateManager();

        players = new ItemBuilder(new ItemStack(Material.COMPASS)).setDisplayName("§ePlayers").getItem();
        playerInfo = new ItemBuilder(new ItemStack(Material.SIGN)).setDisplayName("§cPlayer Info").getItem();
        invSee = new ItemBuilder(new ItemStack(Material.CHEST)).setDisplayName("§aOpen Inventory").getItem();
        vanishOn = new ItemBuilder(new ItemStack(Material.BLAZE_ROD)).setDisplayName("§7Vanish Mode: §aon").getItem();
        vanishOff = new ItemBuilder(new ItemStack(Material.BLAZE_POWDER)).setDisplayName("§7Vanish Mode: §coff").getItem();
        teleport = new ItemBuilder(new ItemStack(Material.REDSTONE_TORCH_ON)).setDisplayName("§7teleport").getItem();
    }

    public void activate(Player player) {
        if (!adminPlayers.containsKey(player.getName())) {
            player.setGameMode(GameMode.CREATIVE);
            player.setFlying(true);
            player.setVelocity(new Vector(0, 0.5, 0));
            player.sendMessage("§bYou entered Admin mode.");
            adminPlayers.put(player.getName(), player.getInventory().getContents());
            HG.INSTANCE.getPlayers().remove(player);
            giveItems(player);
            if(gameStateManager.getCurrentGameState() instanceof IngameState || gameStateManager.getCurrentGameState() instanceof InvincibilityState){
                if(HG.INSTANCE.getPlayers().size()<=1){
                    gameStateManager.setGameState(GameState.ENDING_STATE);
                }
            }
        }
    }

    public void deactivate(Player player) {
        if (adminPlayers.containsKey(player.getName())) {
            if(gameStateManager.getCurrentGameState() instanceof IngameState || gameStateManager.getCurrentGameState() instanceof InvincibilityState){
                player.sendMessage("§cYou cannot leave Admin mode at this stage!");
            }else {
                player.sendMessage("§cYou left Admin mode");
                player.getInventory().setContents(adminPlayers.get(player.getName()));
                HG.INSTANCE.getPlayers().add(player);
                adminPlayers.remove(player.getName());
            }
        }
    }

    public boolean isAdminMode(Player player) {
        return adminPlayers.containsKey(player.getName());
    }

    private void giveItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, players);
        player.getInventory().setItem(1, playerInfo);
        player.getInventory().setItem(4, invSee);
        player.getInventory().setItem(7, teleport);
        if (vanishManager.isVanished(player)) {
            player.getInventory().setItem(8, vanishOn);
        } else {
            player.getInventory().setItem(8, vanishOff);
        }
    }




    public HashMap<String, ItemStack[]> getAdminPlayers() {
        return adminPlayers;
    }
}
