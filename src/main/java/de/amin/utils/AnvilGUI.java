//Created by Duckulus on 04 Aug, 2021 

package de.amin.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

/**
 * Created by chasechocolate.
 */
public class AnvilGUI implements Listener {

    public interface AnvilClickHandler {
        boolean onClick(AnvilGUI menu, String text);
    }

    public static final int SLOT_INPUT_LEFT = 0;
    public static final int SLOT_INPUT_RIGHT = 1;
    public static final int SLOT_OUTPUT = 2;

    private Player player;
    private AnvilClickHandler clickHandler;

    private ItemStack[] items;
    private Inventory inv;

    private Plugin plugin;
    private Listener listener;

    String itemName = "";

    public AnvilGUI(Plugin plugin, Player player, AnvilClickHandler clickHandler){
        this.plugin = plugin;
        this.player = player;
        this.clickHandler = clickHandler;
        this.items = new ItemStack[3];
        this.listener = new Listener(){
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event){
                if(event.getInventory().equals(inv)){
                    event.setCancelled(true);

                    if(event.getWhoClicked() instanceof Player){
                        Player player = (Player) event.getWhoClicked();
                        ItemStack item = event.getCurrentItem();
                        int slot = event.getRawSlot();

                        if(slot == SLOT_OUTPUT){
                            String name;

                            if(item != null){
                                if(item.hasItemMeta()){
                                    ItemMeta meta = item.getItemMeta();

                                    if(meta.hasDisplayName()){
                                        name = ChatColor.stripColor(meta.getDisplayName().trim());
                                    }
                                }

                                if(clickHandler != null){
                                    try{
                                        if(clickHandler.onClick(AnvilGUI.this, itemName)){
                                            player.closeInventory();
                                        }
                                    } catch(Exception e){
                                        e.printStackTrace();
                                        player.sendMessage(ChatColor.RED + "An error occurred (" + e.getClass().getSimpleName() + "): " + e.getMessage());
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event){
                if(event.getPlayer().equals(player)){
                    onClose();
                }
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event){
                if(event.getPlayer().equals(player)){
                    onClose();
                }
            }
        };
    }

    public Player getPlayer(){
        return player;
    }

    public AnvilClickHandler getClickHandler(){
        return clickHandler;
    }

    public AnvilGUI setInputName(String name){
        setItem(SLOT_INPUT_LEFT, new ItemStack(Material.PAPER), name);
        return this;
    }

    public void setOutputName(String name){
        ItemStack item = inv.getItem(SLOT_OUTPUT);

        if(item == null){
            item = inv.getItem(SLOT_INPUT_LEFT);

            if(item == null){
                return;
            }
        }

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        item.setItemMeta(meta);
        inv.setItem(SLOT_OUTPUT, item);
    }

    public Inventory getInventory(){
        return inv;
    }

    public boolean isDead(){
        return player == null && clickHandler == null && items == null && inv == null && listener == null && plugin == null;
    }

    public AnvilGUI open(){
        if(items[SLOT_INPUT_LEFT] == null){
            setInputName("-");
        }

        inv = AnvilNMS.open(this);
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        return this;
    }

    private void destroy(){
        player = null;
        clickHandler = null;
        items = null;
        inv = null;
        HandlerList.unregisterAll(listener);
        listener = null;
        plugin = null;
    }

    public void setItem(int slot, ItemStack item, String name){
        if(name != null){
            ItemMeta meta = item.getItemMeta();

            if(meta.hasDisplayName() && meta.getDisplayName().equals(name)){
                if(inv != null){
                    return;
                }
            } else {
                meta.setDisplayName(name);
                item.setItemMeta(meta);
            }
        }

        items[slot] = item;

        if(slot != SLOT_OUTPUT){
            items[SLOT_OUTPUT] = item;
        }

        if(inv != null){
            inv.setItem(slot, item);

            if(slot != SLOT_OUTPUT){
                inv.setItem(SLOT_OUTPUT, item);
            }
        }
    }

    public void onClose(){
        if(inv != null){
            inv.clear();
        }

        destroy();
    }

    public ItemStack[] getItems(){
        return items;
    }

}
