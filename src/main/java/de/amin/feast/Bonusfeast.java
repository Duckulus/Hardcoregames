//Created by Duckulus on 08 Jul, 2021 

package de.amin.feast;

import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.mechanics.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Bonusfeast {

    public static boolean isBonusFeast = false;

    private Biome biome;
    private int radius;
    private ArrayList<ItemStack> loot;

    public Bonusfeast(){
        isBonusFeast = true;
        radius = 20;
        loot = ((IngameState)HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast().getFeastLoot();
        int x = ThreadLocalRandom.current().nextInt(WorldBorder.size*-1 + 20, WorldBorder.size-20);
        int z = ThreadLocalRandom.current().nextInt(WorldBorder.size*-1 + 20, WorldBorder.size-20);
        int y = Bukkit.getWorld("world").getHighestBlockYAt(x,z);

        Location location = new Location(Bukkit.getWorld("world"), x,y,z);
        biome = location.getBlock().getBiome();

        Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "§7The Bonusfeast spawned in a " + biome.toString() + " biome.");
        Bukkit.getOnlinePlayers().forEach(e -> e.playSound(e.getLocation(), Sound.NOTE_BASS, 1, 1));

        spawnCircle(location, radius);
        spawnChests(location);
    }

    private void spawnCircle(Location location, int radius){
        for (int x = -radius; x <radius ; x++) {
            for (int z = -radius; z <radius ; z++) {
                Block block = location.clone().add(x,0, z).getBlock();
                if(block.getLocation().distance(location)<=radius) {
                    block.setType(Material.GRASS);
                }
            }
        }
    }

    public void spawnChests(Location location) {
        Location origin = location.clone().add(0,1,0);
        origin.clone().getBlock().setType(Material.ENCHANTMENT_TABLE);

        Block chest1 = origin.clone().add(2, 0, 0).getBlock();
        chest1.setType(Material.CHEST);
        addRandomLoot(chest1);

        Block chest2 = origin.clone().add(2, 0, 2).getBlock();
        chest2.setType(Material.CHEST);
        addRandomLoot(chest2);

        Block chest3 = origin.clone().add(2, 0, -2).getBlock();
        chest3.setType(Material.CHEST);
        addRandomLoot(chest3);

        Block chest4 = origin.clone().add(0, 0, 2).getBlock();
        chest4.setType(Material.CHEST);
        addRandomLoot(chest4);

        Block chest5 = origin.clone().add(0, 0, -2).getBlock();
        chest5.setType(Material.CHEST);
        addRandomLoot(chest5);

        Block chest6 = origin.clone().add(-2, 0, 0).getBlock();
        chest6.setType(Material.CHEST);
        addRandomLoot(chest6);

        Block chest7 = origin.clone().add(-2, 0, 2).getBlock();
        chest7.setType(Material.CHEST);
        addRandomLoot(chest7);

        Block chest8 = origin.clone().add(-2, 0, -2).getBlock();
        chest8.setType(Material.CHEST);
        addRandomLoot(chest8);

        Block chest9 = origin.clone().add(1, 0, 1).getBlock();
        chest9.setType(Material.CHEST);
        addRandomLoot(chest9);

        Block chest10 = origin.clone().add(1, 0, -1).getBlock();
        chest10.setType(Material.CHEST);
        addRandomLoot(chest10);

        Block chest11 = origin.clone().add(-1, 0, 1).getBlock();
        chest11.setType(Material.CHEST);
        addRandomLoot(chest11);

        Block chest12 = origin.clone().add(-1, 0, -1).getBlock();
        chest12.setType(Material.CHEST);
        addRandomLoot(chest12);
    }

    private void addRandomLoot(Block block) {
        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            int amount = ThreadLocalRandom.current().nextInt(5, 7);
            for (int i = 0; i < amount; i++) {
                ItemStack item = loot.get(ThreadLocalRandom.current().nextInt(loot.size()));

                if (item.getType().equals(Material.MUSHROOM_SOUP)) {
                    item.setAmount(ThreadLocalRandom.current().nextInt(10));
                } else if (item.getMaxStackSize() > 1) {
                    item.setAmount(ThreadLocalRandom.current().nextInt(item.getMaxStackSize() / 4));
                } else {
                    item.setAmount(1);
                }

                chest.getInventory().setItem(ThreadLocalRandom.current().nextInt(chest.getInventory().getSize()), item);
            }
        }
    }

    public Biome getBiome() {
        return biome;
    }
}
