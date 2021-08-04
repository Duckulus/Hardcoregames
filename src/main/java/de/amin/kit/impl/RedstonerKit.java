//Created by Duckulus on 08 Jul, 2021 

package de.amin.kit.impl;

import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class RedstonerKit extends Kit {

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.PISTON_BASE, 8)).getItem());
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.REDSTONE, 16)).getItem());
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.DIODE, 4)).getItem());
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.DISPENSER, 1)).getItem());
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.SLIME_BALL, 4)).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.DIODE);
    }

    @Override
    public String getName() {
        return "Redstoner";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You start out with all tools");
        description.add("§7necessary to build awesome");
        description.add("§7Redstone contraptions");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }
}
