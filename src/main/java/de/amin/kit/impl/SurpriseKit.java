//Created by Duckulus on 29 Jun, 2021 

package de.amin.kit.impl;

import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SurpriseKit extends Kit {

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GOLDEN_CARROT);
    }

    @Override
    public String getName() {
        return "Surprise";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> desription = new ArrayList<>();
        desription.add("§7You get a random kit as ");
        desription.add("§7soon as the game starts.");
        return desription;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }
}
