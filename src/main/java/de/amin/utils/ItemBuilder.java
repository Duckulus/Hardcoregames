package de.amin.utils;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(ItemStack itemstack) {
        this.item = itemstack;
    }

    public ItemBuilder(Material m){
        this.item = new ItemStack(m);
    }

    public ItemBuilder addAllItemFlags() {
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        this.item.setItemMeta(meta);

        return new ItemBuilder(this.item);
    }

    public ItemBuilder setDisplayName(String name) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setDisplayName(name);
        this.item.setItemMeta(meta);
        return new ItemBuilder(this.item);
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = this.item.getItemMeta();
        meta.setLore(lore);
        this.item.setItemMeta(meta);
        return new ItemBuilder(this.item);
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(flag);
        this.item.setItemMeta(meta);
        return new ItemBuilder(this.item);
    }

    public ItemStack getItem() {
        return this.item;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = this.item.getItemMeta();
        if(unbreakable) {
            meta.spigot().setUnbreakable(true);
        } else {
            meta.spigot().setUnbreakable(false);
        }

        this.item.setItemMeta(meta);
        return new ItemBuilder(this.item);
    }

    public ItemBuilder addUnsafeEnchant(Enchantment ench, int level) {
        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(ench, level, true);
        this.item.setItemMeta(meta);
        return new ItemBuilder(this.item);
    }


    public static boolean hasItemName(String name, ItemStack item) {
        if(item == null || item.getType() == Material.AIR) {
            return false;
        }

        if(item.hasItemMeta()) {
            if(item.getItemMeta().hasDisplayName()) {
                if(item.getItemMeta().getDisplayName().equals(name)) {
                    return true;
                }
            }
        }

        return false;
    }

    public ItemBuilder setAmount(int amount){
        this.item.setAmount(amount);
        return new ItemBuilder(this.item);
    }

}
