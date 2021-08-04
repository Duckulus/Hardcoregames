package de.amin.kit;

import de.amin.hardcoregames.HG;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Kit {

    public abstract void giveItems(Player p);
    public abstract ItemStack getItem();
    public abstract String getName();
    public abstract ArrayList<String> getDescription();
    public abstract KitSetting[] getKitSettings();

    public void activateCooldown(Player player){
        HG.INSTANCE.getCooldown().put(player.getName(), System.currentTimeMillis());
    }

    public boolean isCooldown(Player player, double COOLDOWN){
        HashMap<String, Long> cooldown = HG.INSTANCE.getCooldown();
        if(cooldown.containsKey(player.getName())){
            long be = cooldown.get(player.getName());
            double rest = ((be + (COOLDOWN*1000)) - System.currentTimeMillis());
            if(rest>0){
                player.sendMessage("§cYou are still on Cooldown for§8:§e " +  rest/1000 + (rest/1000==1 ? " Second." : " Seconds."));
                return true;
            }else {
                return false;
            }
        }
        return false;
    }





}
