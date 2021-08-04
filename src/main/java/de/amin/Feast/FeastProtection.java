//Created by Duckulus on 27 Jun, 2021 

package de.amin.Feast;

import de.amin.gamestates.GameState;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class FeastProtection implements Listener {

    @EventHandler
    public void onBlockExplosion(BlockExplodeEvent e){
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return;
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();
        if(Feast.isAnnounced() && !Feast.isFeast()) {
            for (Block b : feast.getBlocks()) {
                e.blockList().remove(b);
            }
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent e){
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return;
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();
        if(Feast.isAnnounced() && !Feast.isFeast()) {
            for (Block b : feast.getBlocks()) {
                e.blockList().remove(b);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        GameState currentGameState = HG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof IngameState){
            if(Feast.isAnnounced() && !Feast.isFeast()){
                Feast feast = ((IngameState) currentGameState).getTimer().getFeast();
                if(feast.getBlocks().contains(e.getBlock())){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        GameState currentGameState = HG.INSTANCE.getGameStateManager().getCurrentGameState();
        if(currentGameState instanceof IngameState){
            if(Feast.isAnnounced() && !Feast.isFeast()){
                Feast feast = ((IngameState) currentGameState).getTimer().getFeast();
                for(Block b : feast.getBlocks()){
                    if(b.getX() == e.getBlock().getX() && b.getZ() == e.getBlock().getZ() && e.getBlock().getY()>b.getY()){
                        e.setCancelled(true);
                    }
                }
            }
        }
    }


}
