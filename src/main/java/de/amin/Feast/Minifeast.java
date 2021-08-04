//Created by Duckulus on 07 Jul, 2021 

package de.amin.Feast;

import de.amin.hardcoregames.HG;
import de.amin.kit.KitManager;
import de.amin.kit.impl.KangarooKit;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Minifeast {

    private YamlConfiguration config;

    private final int xPos;
    private int yPos;
    private final int zPos;

    private final Location location;

    private int radius;

    private final ArrayList<ItemStack> feastLoot = new ArrayList<>();

    private final KitManager kitManager;


    public Minifeast() {
        FileConfiguration config = HG.INSTANCE.getFileConfig();
        int bordersize = config.getInt("mechanics.bordersize");

        kitManager = HG.INSTANCE.getKitManager();

        xPos = ThreadLocalRandom.current().nextInt(bordersize * -1 + radius, bordersize - radius);
        zPos = ThreadLocalRandom.current().nextInt(bordersize * -1 + radius, bordersize - radius);
        yPos = Bukkit.getWorld("world").getHighestBlockYAt(xPos, yPos) + 1;

        location = new Location(Bukkit.getWorld("world"), xPos, yPos, zPos);

        radius = 3;

        feastLoot.add(new ItemStack(Material.IRON_INGOT));
        feastLoot.add(new ItemStack(Material.RED_MUSHROOM));
        feastLoot.add(new ItemStack(Material.BROWN_MUSHROOM));
        feastLoot.add(new ItemStack(Material.BUCKET));

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

        feastLoot.add(poisonPotion);
        feastLoot.add(weaknessPotion);
        feastLoot.add(damagePotion);
        feastLoot.add(slownessPotion);
        spawn();
    }

    private void spawn() {
        spawnCircle(location, radius);
        spawnChests();

        announce();
    }

    private void spawnChests() {
        Location origin = location.clone().add(0, 1, 0);
        origin.clone().getBlock().setType(Material.ENCHANTMENT_TABLE);

        Block chest1 = origin.clone().add(1, 0, 1).getBlock();
        chest1.setType(Material.CHEST);
        addRandomLoot(chest1);

        Block chest2 = origin.clone().add(-1, 0, 1).getBlock();
        chest2.setType(Material.CHEST);
        addRandomLoot(chest2);

        Block chest3 = origin.clone().add(1, 0, -1).getBlock();
        chest3.setType(Material.CHEST);
        addRandomLoot(chest3);

        Block chest4 = origin.clone().add(-1, 0, -1).getBlock();
        chest4.setType(Material.CHEST);
        addRandomLoot(chest4);
    }


    private void spawnCircle2(Location loc, int radius, Material material) {
        for (double rr = 0.0D; rr < radius; rr = rr + 0.2D) {
            for (int i = 0; i < 250; i++) {
                int x = (int) (rr * Math.sin(i));
                int z = (int) (rr * Math.cos(i));
                loc.getBlock().getRelative(x, 0, z).setType(material);
                for (int ii = 0; ii < 40; ii++) {
                    loc.getBlock().getRelative(x, ii + 1, z).setType(Material.AIR);
                }
            }
        }
    }

    private void spawnCircle(Location location, int radius){
        for (int x = -radius; x <radius ; x++) {
            for (int z = -radius; z <radius ; z++) {
                Block block = location.clone().add(x,0, z).getBlock();
                if(block.getLocation().distance(location)<=radius) {
                    block.setType(Material.GLASS);
                }

            }
        }
    }


    private void addRandomLoot(Block block) {
        if (block.getState() instanceof Chest) {
            Chest chest = (Chest) block.getState();
            int amount = ThreadLocalRandom.current().nextInt(5, 7);
            for (int i = 0; i < amount; i++) {
                ItemStack item = feastLoot.get(ThreadLocalRandom.current().nextInt(feastLoot.size()));

                if (item.getType().equals(Material.IRON_INGOT)) {
                    item.setAmount(1);
                } else if (item.getType().equals(Material.BUCKET)) {
                    if (new Random().nextInt(10) > 5) {
                        item.setAmount(0);
                    }
                } else if (item.getMaxStackSize() > 1) {
                    item.setAmount(ThreadLocalRandom.current().nextInt(item.getMaxStackSize() / 4));
                } else {
                    item.setAmount(1);
                }

                chest.getInventory().setItem(ThreadLocalRandom.current().nextInt(chest.getInventory().getSize()), item);
            }
        }
    }


    private void announce() {
        String x;
        String z;

        if (xPos > 0) {
            x = "+";
        } else {
            x = "-";
        }

        if (zPos > 0) {
            z = "+";
        } else {
            z = "-";
        }

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (kitManager.getKit(player) instanceof KangarooKit) {
                TextComponent message = new TextComponent("§cMinifeast spawned");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + xPos + " " + yPos + " " + zPos));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Click to teleport!")}));
                player.spigot().sendMessage(message);
            } else {
                TextComponent message = new TextComponent("§cA minifeast spawned at (x: " + x + ") and (z: " + z + ")!");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + xPos + " " + yPos + " " + zPos));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent("Click to teleport!")}));
                player.spigot().sendMessage(message);
            }
        }
    }


}
