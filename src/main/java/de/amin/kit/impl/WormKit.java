package de.amin.kit.impl;

import de.amin.Feast.Feast;
import de.amin.gamestates.IngameState;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class WormKit extends Kit implements Listener {
    @Override
    public void giveItems(Player p) {
        p.getInventory().addItem(new ItemStack(Material.DIRT, 64));
    }

    @Override
    public ItemStack getItem() {
        return new ItemBuilder(new ItemStack(Material.DIRT)).setDisplayName("§aWorm").getItem();
    }

    @Override
    public String getName() {
        return "Worm";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7You can instantly break dirt, grass,");
        description.add("§7sand and gravel and you do not get ");
        description.add("§7any falldamage if you land on them.");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState)return;
        if(!(HG.INSTANCE.getKitManager().getKitHashMap().get(e.getPlayer().getName()) instanceof  WormKit))return;
        if(!e.getAction().equals(Action.LEFT_CLICK_BLOCK))return;

        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();
        if(Feast.isAnnounced() && !Feast.isFeast() && feast.getBlocks().contains(e.getClickedBlock()))return;

        if(e.getClickedBlock().getType().equals(Material.DIRT) || e.getClickedBlock().getType().equals(Material.GRASS)){
            Location blockLocation = e.getClickedBlock().getLocation();
            blockLocation.getBlock().setType(Material.AIR);
            blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.DIRT));
        }else if(e.getClickedBlock().getType().equals(Material.SAND)){
            Location blockLocation = e.getClickedBlock().getLocation();
            blockLocation.getBlock().setType(Material.AIR);
            blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.SAND));
        }else if(e.getClickedBlock().getType().equals(Material.GRAVEL)){
            Location blockLocation = e.getClickedBlock().getLocation();
            blockLocation.getBlock().setType(Material.AIR);
            blockLocation.getWorld().dropItemNaturally(blockLocation, new ItemStack(Material.GRAVEL));
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!e.getEntityType().equals(EntityType.PLAYER))return;
        if(!(HG.INSTANCE.getKitManager().getKitHashMap().get(e.getEntity().getName()) instanceof  WormKit))return;
        if(!e.getCause().equals(EntityDamageEvent.DamageCause.FALL))return;
        Player p = (Player) e.getEntity();
        Location loc = p.getLocation();
        loc.setY(loc.getY()-1);
        if(loc.getBlock().getType().equals(Material.DIRT) ||loc.getBlock().getType().equals(Material.GRASS) ||loc.getBlock().getType().equals(Material.SAND) ||loc.getBlock().getType().equals(Material.GRAVEL)){
            e.setCancelled(true);
        }
    }
}


