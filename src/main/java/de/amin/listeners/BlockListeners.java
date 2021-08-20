package de.amin.listeners;

import de.amin.gamestates.*;
import de.amin.hardcoregames.HG;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.PlainDocument;
import java.util.Arrays;
import java.util.List;

public class BlockListeners implements Listener {


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        GameState currentGameState = HG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof LobbyState || currentGameState instanceof EndingState){
            event.setCancelled(true);
        } else {
            switch (event.getBlock().getType()) {
                case RED_MUSHROOM:
                case BROWN_MUSHROOM:
                    event.setCancelled(true);
                    event.getBlock().getDrops().forEach( e -> player.getInventory().addItem(e));
                    event.getBlock().setType(Material.AIR);
                    break;

            }
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getItemInHand().getItemMeta().spigot().isUnbreakable()){
            event.setCancelled(true);
        }
        GameState currentGameState = HG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof LobbyState){
            event.setCancelled(true);
        }
    }


}
