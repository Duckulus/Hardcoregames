package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class PhantomKit extends Kit implements Listener {

    private double seconds;
    private HashMap<String, Long> cooldownList = HG.INSTANCE.getCooldown();
    private int taskID;

    private KitSetting cooldown = new KitSetting(this, "cooldown", 20, 0, 100);
    private KitSetting duration = new KitSetting(this, "duration", 10, 5, 100);


    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.FEATHER)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.FEATHER)).setDisplayName("§a" + getName()).getItem();
    }

    @Override
    public String getName() {
        return "Phantom";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Use your feather to gain the tem-");
        description.add("§7porary ability to fly.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown, duration};
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();
        KitManager kitManager = HG.INSTANCE.getKitManager();
        Player p = e.getPlayer();
        String prefix = HG.INSTANCE.PREFIX;


        if(!e.hasItem())return;
        if(!(kitManager.getKitHashMap().get(p.getName()) instanceof PhantomKit))return;
        if(!e.getItem().getType().equals(Material.FEATHER))return;
        if(!e.getItem().getItemMeta().spigot().isUnbreakable())return;

        if(isCooldown(p, cooldown.getValue() + duration.getValue()))return;
        p.setAllowFlight(true);
        p.setFlying(true);
        p.sendMessage("§7You are now in Flymode");
        for(Player player : HG.INSTANCE.getPlayers()){
                player.playSound(p.getLocation(), Sound.ENDERDRAGON_GROWL, 5.0F, 1.0F);
            }
        activateCooldown(e.getPlayer());
        startCountdown(p);
        }


    public void startCountdown(Player p)    {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            int seconds = (int) duration.getValue();
            @Override
            public void run() {
                switch(seconds){
                    case 3: case 2: case 1:
                        p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        p.sendMessage("§7Flight will end in §e" + seconds + (seconds==1 ? " Second." : " Seconds."));
                        break;
                    case 0:
                        p.playSound(p.getLocation(), Sound.GLASS, 1.0F, 1.0F);
                        p.sendMessage("§cYour Flight ended.");
                        p.setAllowFlight(false);
                        HG.INSTANCE.getLaunchedPlayers().put(p.getName(), System.currentTimeMillis());
                        break;
                    default:
                        break;
                }
                seconds--;
            }
        }, 0, 20);
    }





}

