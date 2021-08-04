//Created by Duckulus on 08 Jul, 2021 

package de.amin.kit.impl;

import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GrandpaKit extends Kit {

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.STICK)).addUnsafeEnchant(Enchantment.KNOCKBACK, 2).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.STICK)).addUnsafeEnchant(Enchantment.KNOCKBACK, 2).getItem();
    }

    @Override
    public String getName() {
        return "Grandpa";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You start with a stick that can");
        description.add("§7Knock away your enemies");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }
}
