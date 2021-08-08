package de.amin.kit.impl;

import de.amin.feast.Feast;
import de.amin.gamestates.IngameState;
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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class JackhammerKit extends Kit implements Listener {


    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.STONE_AXE)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.STONE_AXE)).setDisplayName("§a" + getName()).setUnbreakable(true).getItem();
    }

    @Override
    public String getName() {
        return "Jackhammer";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7When you break a Block all the ones");
        description.add("§7above it or bewlow it will break ");
        description.add("§7aswell");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        KitManager kitManager = HG.INSTANCE.getKitManager();
        Location loc = block.getLocation();
        if (!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState)) return;
        if (!(kitManager.getKitHashMap().get(player.getName()) instanceof JackhammerKit)) return;
        if (!player.getItemInHand().getType().equals(Material.STONE_AXE)) return;
        if (!player.getItemInHand().getItemMeta().spigot().isUnbreakable()) return;
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();
        if (player.getLocation().getPitch() < 0) {
            for (int i = 256; i > block.getLocation().getY(); i--) {
                loc.setY(i);
                if (feast == null) {
                    loc.getBlock().setType(Material.AIR);
                } else {
                    if (loc.getBlock().getType() != Material.BEDROCK && !feast.getBlocks().contains(loc.getBlock())) {
                        loc.getBlock().setType(Material.AIR);
                    }
                }
            }
        } else {
            for (int i = (int) loc.getY(); i > 0; i--) {
                loc.setY(i);
                if (feast == null) {
                    loc.getBlock().setType(Material.AIR);
                } else {
                    if (loc.getBlock().getType() != Material.BEDROCK && !feast.getBlocks().contains(loc.getBlock())) {
                        loc.getBlock().setType(Material.AIR);
                    }
                }
            }
        }
    }

}
