//Created by Duckulus on 05 Jul, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class NinjaKit extends Kit implements Listener {

    private KitManager kitManager;
    private GameStateManager gameStateManager;

    private KitSetting cooldown = new KitSetting(this, "cooldown", 10, 0, 100);
    private KitSetting time = new KitSetting(this, "time", 20, 0, 100);

    private HashMap<String, String> ninjas;

    public NinjaKit() {
        kitManager = HG.INSTANCE.getKitManager();
        gameStateManager = HG.INSTANCE.getGameStateManager();
        ninjas = new HashMap<>();

    }

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.INK_SACK);
    }

    @Override
    public String getName() {
        return "Ninja";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Sneak to teleport behind the");
        description.add("§7last player you hit");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{cooldown, time};
    }

    @EventHandler
    public void onPlayerHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {

            Player player = (Player) e.getDamager();
            Player target = (Player) e.getEntity();

            if (!(kitManager.getKit(player) instanceof NinjaKit)) return;
            if (!(gameStateManager.getCurrentGameState() instanceof IngameState)) return;


            ninjas.put(player.getName(), target.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, () -> {
                ninjas.remove(player.getName());
            }, (long) (time.getValue() * 20L));
        }

    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e) {
        if(e.getPlayer().isSneaking())return;

        Player player = e.getPlayer();
        Player target = null;

        if(ninjas.containsKey(player.getName())) {
            target = Bukkit.getPlayer(ninjas.get(player.getName()));
        }

        if (!(kitManager.getKit(player) instanceof NinjaKit)) return;
        if (!(gameStateManager.getCurrentGameState() instanceof IngameState)) return;

        if (isCooldown(player, cooldown.getValue())) return;

        if(target==null)return;

        player.setVelocity(new Vector(0,0,0));
        player.teleport(target.getLocation().add(target.getLocation().getDirection().multiply(-1)));
        player.setVelocity(new Vector(0,0,0));
        player.sendMessage("§dBackstabbed your enemy!");
        target.sendMessage("§dYou got backstabbed by a Ninja!");
        activateCooldown(player);

    }
}
