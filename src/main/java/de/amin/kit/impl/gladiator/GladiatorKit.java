//Created by Duckulus on 04 Jul, 2021 

package de.amin.kit.impl.gladiator;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GladiatorKit extends Kit implements Listener {

    private KitManager kitManager;
    private GameStateManager gameStateManager;
    private KitSetting width;
    private KitSetting length;
    private KitSetting height;

    public GladiatorKit() {
        kitManager = HG.INSTANCE.getKitManager();
        gameStateManager = HG.INSTANCE.getGameStateManager();

        width = new KitSetting(this, "width", 23, 1, 100);
        length = new KitSetting(this, "length", 23, 1, 100);
        height = new KitSetting(this, "height", 10, 1, 100);
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.IRON_FENCE)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.IRON_FENCE);
    }

    @Override
    public String getName() {
        return "Gladiator";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You and your opponent are");
        description.add("§7Teleported to an arena and");
        description.add("§7you have to fight till death");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{width, length, height};
    }


    @EventHandler
    public void onPlayerRightClick(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) return;
        if (!(gameStateManager.getCurrentGameState() instanceof IngameState)) return;
        Player player = e.getPlayer();
        Player rightClicked = (Player) e.getRightClicked();
        if(!HG.INSTANCE.getPlayers().contains(rightClicked)) return;
        if (!(kitManager.getKitHashMap().get(player.getName()) instanceof GladiatorKit)) return;
        if (player.getItemInHand() == null || !player.getItemInHand().getType().equals(Material.IRON_FENCE) || !player.getItemInHand().getItemMeta().spigot().isUnbreakable())
            return;
        if (Gladiator.isInFight(player) || Gladiator.isInFight(rightClicked)) return;

        Gladiator gladiator = new Gladiator(player, rightClicked);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        for (Gladiator g : Gladiator.getGladiators()) {
            if (g.getGladiatorBlocks().contains(e.getBlock())) {
                Block b = e.getBlock();
                if (b.getType().equals(Material.STAINED_GLASS) && b.getData() == 0) {
                    e.setCancelled(true);
                    b.setData((byte) 4);
                } else if (b.getType().equals(Material.STAINED_GLASS) && b.getData() == 4) {
                    e.setCancelled(true);
                    b.setData((byte) 1);
                } else if (b.getType().equals(Material.STAINED_GLASS) && b.getData() == 1) {
                    e.setCancelled(true);
                    b.setData((byte) 14);
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        for (Gladiator g : Gladiator.getGladiators()) {
            if (g.getPlayers().contains(e.getEntity().getName())) {
                for (String p : g.getPlayers()) {
                    if (!e.getEntity().getName().equals(p)) {
                        Player winner = Bukkit.getPlayer(p);
                        g.end();

                        for (ItemStack itemStack : e.getDrops()) {
                            winner.getWorld().dropItemNaturally(winner.getLocation(), itemStack);
                        }

                        e.getDrops().clear();


                    }
                }
            }
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent e) {
        for (Gladiator g : Gladiator.getGladiators()) {
            if (g.getPlayers().contains(e.getPlayer().getName())) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
                    @Override
                    public void run() {
                        g.end();
                    }
                }, 20L);

            }
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        if (!(kitManager.getKitHashMap().get(player.getName()) instanceof GladiatorKit)) return;
        if (!e.getBlock().getType().equals(Material.IRON_FENCE)) return;
        if (!e.getItemInHand().getItemMeta().spigot().isUnbreakable()) return;

        e.setCancelled(true);
    }
}
