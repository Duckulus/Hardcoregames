//Created by Duckulus on 30 Jun, 2021 

package de.amin.managers;

import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemManager implements Listener {

    private HashMap<String, ArrayList<ItemStack>> items;

    public ItemManager(){
        items = new HashMap<>();
        run();
    }

    public void giveItemsLater(Player player, ItemStack itemStack){
        if(player.getInventory().firstEmpty()==-1) {
            ArrayList<ItemStack> oldItems = items.get(player.getName());
            if (oldItems == null) {
                oldItems = new ArrayList<>();
            }
            oldItems.add(itemStack);
            items.put(player.getName(), oldItems);
        }else {
            player.getInventory().addItem(itemStack);
        }
    }

    private void run(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, () -> {
            for(String s : items.keySet()){
                if(Bukkit.getPlayer(s)!=null && Bukkit.getPlayer(s).getInventory().firstEmpty()!=-1){
                    if(items.get(s).size()>0) {
                        Bukkit.getPlayer(s).getInventory().addItem(items.get(s).get(0));
                        items.get(s).remove(0);
                    }
                }
            }
        }, 20L, 10L);
    }

    public HashMap<String, ArrayList<ItemStack>> getItems() {
        return items;
    }
}
