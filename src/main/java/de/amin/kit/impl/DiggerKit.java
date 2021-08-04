package de.amin.kit.impl;

import de.amin.Feast.Feast;
import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DiggerKit extends Kit implements Listener {

    private final int[] xOff = new int[]{-2, -1, 0, 1, 2};
    private final int[] yOff = new int[]{0, -1, -2, -3, -4, -5};
    private final int[] zOff = new int[]{-2, -1, 0, 1, 2};

    private KitSetting cooldown = new KitSetting(this, "cooldown", 10, 0, 100);


    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.DRAGON_EGG, 25)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.DRAGON_EGG)).setDisplayName("§aDigger").setUnbreakable(true).getItem();
    }

    @Override
    public String getName() {
        return "Digger";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Place a dragon egg to create a");
        description.add("§7hole in the ground.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown};
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (!e.getBlock().getType().equals(Material.DRAGON_EGG)) return;

        Player player = e.getPlayer();
        GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();
        KitManager kitManager = HG.INSTANCE.getKitManager();

        if (!(kitManager.getKitHashMap().get(player.getName()) instanceof DiggerKit)) return;
        if (!(gameStateManager.getCurrentGameState() instanceof IngameState)) {
            e.setCancelled(true);
            return;
        }

        if(isCooldown(player, cooldown.getValue()))return;

        e.getBlock().setType(Material.AIR);
        player.sendMessage("Digging Hole..");
        activateCooldown(player);
        Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                digHole(e.getBlock().getLocation());
            }
        }, 3 * 20L);
    }

    @EventHandler
    public void onEggRightClick(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.DRAGON_EGG)) {
            e.setCancelled(true);
        }
    }

    private void digHole(Location location) {
        Location temp = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();

        for (int h = 0; h <= xOff.length - 1; h++) {
            for (int i = 0; i <= yOff.length - 1; i++) {
                for (int j = 0; j <= zOff.length - 1; j++) {
                    location.setX(temp.getX());
                    location.setY(temp.getY());
                    location.setZ(temp.getZ());

                    location.add(xOff[h], yOff[i], zOff[j]);
                    if (feast == null) {
                        location.getBlock().setType(Material.AIR);
                    } else {
                        if (!feast.getBlocks().contains(location.getBlock())) {
                            location.getBlock().setType(Material.AIR);
                        }
                    }
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.playSound(location, Sound.DIG_STONE, 1.0F, 1.0F);
                    }
                    location.getWorld().playEffect(location, Effect.ENDER_SIGNAL, 10, 10);
                }
            }
        }
    }

}


