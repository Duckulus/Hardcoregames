//Created by Duckulus on 30 Jun, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class KangarooKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final ArrayList<String> isReady;


    private KitSetting forwardBoost = new KitSetting(this, "forward boost", 2, 0, 20);
    private KitSetting upwardBoost = new KitSetting(this, "upward boost", 0.6, 0, 20);

    public KangarooKit() {
        kitManager = HG.INSTANCE.getKitManager();
        isReady = new ArrayList<>();

        run();
    }

    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemBuilder(new ItemStack(Material.FIREWORK)).setUnbreakable(true).getItem());
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.FIREWORK);
    }

    @Override
    public String getName() {
        return "Kangaroo";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Use your Rocket to jump around");
        description.add("§7like a kangaroo and you get less");
        description.add("§7falldamage");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{forwardBoost, upwardBoost};
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(kitManager.getKitHashMap().get(player.getName()) instanceof KangarooKit)) return;
        if (!player.getItemInHand().getType().equals(Material.FIREWORK)) return;
        e.setCancelled(true);
        if (isReady.contains(player.getName())) return;
        if (player.isSneaking()) {
            player.setVelocity(new Vector(player.getLocation().getDirection().getX() * forwardBoost.getValue(),
                    upwardBoost.getValue(),
                    player.getLocation().getDirection().getZ() * forwardBoost.getValue()));
            isReady.add(player.getName());
        } else {
            player.setVelocity(new Vector(0, 1, 0));
            isReady.add(player.getName());
        }

        //So the rocket doesnt get used
    }

    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (isReady.contains(player.getName())) {
                        if (player.isOnGround()) {
                            isReady.remove(player.getName());
                        }
                    }
                }
            }
        }, 0L, 1L);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))return;
        Player player = (Player) e.getEntity();
        if(!(kitManager.getKit(player) instanceof KangarooKit))return;
        if(!e.getCause().equals(EntityDamageEvent.DamageCause.FALL))return;
        if(isReady.contains(player.getName())){
            if(e.getFinalDamage()>4){
                e.setDamage(4);
            }
        }else {
            e.setDamage(e.getFinalDamage()/2);
        }
    }
}
