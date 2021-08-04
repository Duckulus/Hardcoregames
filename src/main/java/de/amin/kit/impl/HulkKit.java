package de.amin.kit.impl;

import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class HulkKit extends Kit implements Listener {

    private KitSetting boost = new KitSetting(this, "boost", 1.5, 0, 20);
    private KitSetting cooldown = new KitSetting(this, "cooldown", 5, 0, 100);

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.PISTON_BASE)).setDisplayName("§a" + getName()).getItem();
    }

    @Override
    public String getName() {
        return "Hulk";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Pick up other Players and ");
        description.add("§7throw them around.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[]{boost, cooldown};
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        Entity victim = e.getRightClicked();
        if(!e.getPlayer().getItemInHand().getType().equals(Material.AIR))
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return;
        if(!(HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()) instanceof HulkKit))return;
        if(isCooldown(player, cooldown.getValue()))return;
        player.setPassenger(victim);
        if(victim instanceof Player){
            victim.sendMessage("§aA Hulk picked you up.");
        }
        activateCooldown(player);
    }

    @EventHandler
    public void onThrow(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(!(HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()) instanceof HulkKit))return;
        if(player.getPassenger() ==null)return;
        Entity passenger = player.getPassenger();
        player.eject();
        passenger.setVelocity(player.getLocation().getDirection().normalize().multiply(boost.getValue()));
    }
}
