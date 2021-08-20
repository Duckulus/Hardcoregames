//Created by Duckulus on 29 Jun, 2021 

package de.amin.kit.impl;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import de.amin.mechanics.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class ScoutKit extends Kit implements Listener {

    private final KitManager kitManager;
    private final PotionType effect;

    private static long potionDelay;

    private KitSetting potionAmount = new KitSetting(this, "Potion Amount", 2, 0, 100);

    public ScoutKit() {
        kitManager = HG.INSTANCE.getKitManager();
        potionDelay = 5*60;
        effect = PotionType.SPEED;
    }


    @Override
    public void giveItems(Player p) {
        ItemStack potion = new ItemStack(Material.POTION);
        Potion speedPotion = new Potion(1);
        speedPotion.setLevel(2);
        speedPotion.setSplash(true);
        speedPotion.setType(effect);
        speedPotion.apply(potion);
        potion.setAmount((int) potionAmount.getValue());
        p.getInventory().addItem(potion);
    }

    @Override
    public ItemStack getItem() {
        ItemStack potion = new ItemStack(Material.POTION);
        Potion speedPotion = new Potion(1);
        speedPotion.setLevel(2);
        speedPotion.setSplash(true);
        speedPotion.setType(effect);
        speedPotion.apply(potion);
        return potion;
    }

    @Override
    public String getName() {
        return "Scout";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("");
        description.add("§7You start with 2 speed potions");
        description.add("§7that regenerate over time and ");
        description.add("§7while having speed you do not take");
        description.add("§7any falldamage");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{potionAmount};
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (!(kitManager.getKitHashMap().get(player.getName()) instanceof ScoutKit)) return;
        if (!event.getCause().equals(EntityDamageEvent.DamageCause.FALL)) return;
        if (player.hasPotionEffect(PotionEffectType.SPEED)) {
            if (event.getFinalDamage() > 4) {
                event.setDamage(4);
            }
        }
    }


    public static void speedPotionRunnable(ItemManager itemManager) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                PotionType effect = PotionType.SPEED;
                ItemStack potion = new ItemStack(Material.POTION);
                Potion speedPotion = new Potion(1);
                speedPotion.setLevel(2);
                speedPotion.setSplash(true);
                speedPotion.setType(effect);
                speedPotion.apply(potion);
                potion.setAmount(2);

                for (Player player : HG.INSTANCE.getPlayers()) {
                    if (HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()) instanceof ScoutKit) {
                        itemManager.giveItemsLater(player, potion);
                        player.sendMessage("§aYou have been given two speed pots.");
                    }
                }
            }
        }, potionDelay * 20, 20 * potionDelay);
    }


}
