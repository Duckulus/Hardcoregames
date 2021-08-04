package de.amin.kit.impl;

import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class LauncherKit extends Kit {

    private KitSetting startAmount = new KitSetting(this, "start amount", 20, 0, 100);

    @Override
    public void giveItems(Player p) {
        ItemStack launcher = new ItemStack(Material.SPONGE);
        launcher.setAmount((int) startAmount.getValue());
        p.getInventory().addItem(launcher);
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.SPONGE)).setDisplayName("§a" +"Launcher").setUnbreakable(true).getItem();
    }

    @Override
    public String getName() {
        return "Launcher";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You start with 20 launcher blocks.");
        description.add("§7When you step on them they will ");
        description.add("§7boost you up in the air");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{startAmount};
    }
}


