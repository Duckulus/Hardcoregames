//Created by Duckulus on 27 Jun, 2021 

package de.amin.Feast;

import de.amin.hardcoregames.HG;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Feast {

    private static boolean isFeast;
    private static boolean isAnnounced;



    private int xPos;
    private int yPos;
    private int zPos;
    private int taskID;
    private ArrayList<Block> blocks;
    private ArrayList<ItemStack> feastLoot;
    int secondsToFeast;

    public Feast() {
        isFeast = false;
        isAnnounced = false;


        xPos = ThreadLocalRandom.current().nextInt(-100, 100);
        zPos = ThreadLocalRandom.current().nextInt(-100, 100);
        yPos = Bukkit.getWorld("world").getHighestBlockYAt(xPos, zPos) + 1;

        blocks = new ArrayList<>();

        secondsToFeast = 300;

        feastLoot = new ArrayList<>();
        feastLoot.add(new ItemStack(Material.DIAMOND_SWORD));
        feastLoot.add(new ItemStack(Material.DIAMOND_BOOTS));
        feastLoot.add(new ItemStack(Material.DIAMOND_LEGGINGS));
        feastLoot.add(new ItemStack(Material.DIAMOND_CHESTPLATE));
        feastLoot.add(new ItemStack(Material.DIAMOND_HELMET));
        feastLoot.add(new ItemStack(Material.MUSHROOM_SOUP));
        feastLoot.add(new ItemStack(Material.WATER_BUCKET));
        feastLoot.add(new ItemStack(Material.LAVA_BUCKET));
        feastLoot.add(new ItemStack(Material.ENDER_PEARL));
        feastLoot.add(new ItemStack(Material.WEB));

        ItemStack strengthPotion = new ItemStack(Material.POTION, (byte) 1);
        Potion strengthPot = new Potion(1);
        strengthPot.setSplash(true);
        strengthPot.setType(PotionType.STRENGTH);
        strengthPot.apply(strengthPotion);

        ItemStack poisonPotion = new ItemStack(Material.POTION, (byte) 1);
        Potion poisonPot = new Potion(1);
        poisonPot.setSplash(true);
        poisonPot.setType(PotionType.POISON);
        poisonPot.apply(poisonPotion);

        ItemStack weaknessPotion = new ItemStack(Material.POTION, (byte) 1);
        Potion weaknessPot = new Potion(1);
        weaknessPot.setSplash(true);
        weaknessPot.setType(PotionType.WEAKNESS);
        weaknessPot.apply(weaknessPotion);

        ItemStack damagePotion = new ItemStack(Material.POTION, (byte) 1);
        Potion damagePot = new Potion(1);
        damagePot.setSplash(true);
        damagePot.setType(PotionType.INSTANT_DAMAGE);
        damagePot.apply(damagePotion);

        ItemStack slownessPotion = new ItemStack(Material.POTION, (byte) 1);
        Potion slownessPot = new Potion(1);
        slownessPot.setSplash(true);
        slownessPot.setType(PotionType.SLOWNESS);
        slownessPot.apply(slownessPotion);

        feastLoot.add(strengthPotion);
        feastLoot.add(poisonPotion);
        feastLoot.add(weaknessPotion);
        feastLoot.add(damagePotion);
        feastLoot.add(slownessPotion);

        runFeast();
    }

    public void runFeast() {
        isAnnounced = true;
        spawnFeast();
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                switch (secondsToFeast) {
                    case 119:
                        for(Entity e : Bukkit.getWorld("world").getNearbyEntities(new Location(Bukkit.getWorld("world"), xPos, yPos, zPos), 30, 30, 30)){
                            if(e.getType().equals(EntityType.DROPPED_ITEM)){
                                e.remove();
                            }
                        }
                        break;
                    case 300:
                    case 240:
                    case 180:
                    case 120:
                    case 90:
                    case 60:
                    case 30:
                    case 15:
                    case 10:
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                    case 1:
                        for(Player current : Bukkit.getOnlinePlayers()){
                            current.playSound(current.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        }
                        Bukkit.broadcastMessage("§cFeast will spawn at [" + xPos + "," + yPos + "," + zPos + "] in " + secondsToFeast + " seconds.");
                        break;
                    case 0:
                        for(Player current : Bukkit.getOnlinePlayers()){
                            current.playSound(current.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                        }
                        spawnChests(new Location(Bukkit.getWorld("world"), xPos, yPos, zPos));
                        isFeast = true;
                        Bukkit.getScheduler().cancelTask(taskID);
                        break;
                    default:
                        break;
                }
                secondsToFeast--;
            }
        }, 0L, 20L);
    }



    private void spawnFeast() {
        spawnCircle(new Location(Bukkit.getWorld("world"), xPos, yPos, zPos), 20, Material.GRASS);
        for (int i = yPos+1; i <200 ; i++) {
            spawnCircle(new Location(Bukkit.getWorld("world"), xPos, i, zPos), 20, Material.AIR);
        }
    }



    private void spawnCircle(Location location, int radius, Material material){
        for (int x = -radius; x <radius ; x++) {
            for (int z = -radius; z <radius ; z++) {
                Block block = location.clone().add(x,0, z).getBlock();
                if(block.getLocation().distance(location)<radius) {
                    block.setType(Material.GRASS);
                    blocks.add(block);
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
                ItemStack item = feastLoot.get(ThreadLocalRandom.current().nextInt(feastLoot.size()));

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

    public static boolean isFeast() {
        return isFeast;
    }

    public static boolean isAnnounced() {
        return isAnnounced;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getzPos() {
        return zPos;
    }

    public int getSecondsToFeast() {
        return secondsToFeast;
    }

    public ArrayList<ItemStack> getFeastLoot() {
        return feastLoot;
    }
}
