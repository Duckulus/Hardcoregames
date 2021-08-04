package de.amin.kit.impl;

import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class NoneKit extends Kit {


    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.PAPER)).setDisplayName("§a" +"None").getItem();
    }

    @Override
    public String getName() {
        return "None";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Play this kit if you want an extra");
        description.add("§7challenge. It doesn't have any ");
        description.add("§7special properties");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }
}
