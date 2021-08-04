//Created by Duckulus on 21 Jul, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class XrayKit extends Kit implements Listener {

    private final KitManager kitManager;


    private KitSetting cooldown = new KitSetting(this, "cooldown", 25, 0, 100);
    private KitSetting duration = new KitSetting(this, "Duration", 7, 0, 100);
    private KitSetting radius = new KitSetting(this, "radius", 20, 0, 100);

    public XrayKit() {
        kitManager = HG.INSTANCE.getKitManager();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.GLASS)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GLASS);
    }

    @Override
    public String getName() {
        return "Xray";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7See through walls to locate");
        description.add("§7ores easily.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown, duration, radius};
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (!(kitManager.getKit(player) instanceof XrayKit)) return;
        if (player.getItemInHand() == null || !player.getItemInHand().getType().equals(Material.GLASS) || !player.getItemInHand().getItemMeta().spigot().isUnbreakable())
            return;
        if (isCooldown(player, cooldown.getValue())) return;

        HashMap<Block, Material> blocks = turnBlocksIntoGlass(player);

        activateCooldown(player);

        Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, () -> revertBlocks(blocks), (long) (duration.getValue() * 20L));
    }

    private HashMap<Block, Material> turnBlocksIntoGlass(Player player) {
        HashMap<Block, Material> blocks = new HashMap<>();

        for (int i = 0; i <radius.getValue()*2 ; i++) {
            int y = (-i - 1);
            Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, () -> {
                for (int x = (int) -radius.getValue(); x < radius.getValue(); x++) {
                    for (int z = (int) -radius.getValue(); z < radius.getValue(); z++) {
                        Block block = player.getLocation().clone().add(x, y, z).getBlock();
                        if (!block.getType().equals(Material.AIR)
                                && !block.getType().equals(Material.BEDROCK) && !block.getType().name().toLowerCase().contains("ore")) {
                            blocks.put(block, block.getType());
                            if(new Random().nextInt(100) == 25){
                                block.setType(Material.GLOWSTONE);
                            }else {
                                block.setType(Material.GLASS);
                            }
                        }
                    }
                }

                for(Player p : Bukkit.getOnlinePlayers()){
                    p.playSound(player.getLocation().clone().add(0,y,0), Sound.DIG_STONE, 1, 1);
                }
            }, i);

        }



        return blocks;
    }

    private void revertBlocks(HashMap<Block, Material> blocks) {
        blocks.forEach(Block::setType);
    }

}