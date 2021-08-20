package de.amin.gamestates;

import de.amin.countdowns.IngameTimer;
import de.amin.hardcoregames.HG;
import de.amin.kit.impl.ScoutKit;
import de.amin.mechanics.ItemManager;
import org.bukkit.Bukkit;

public class IngameState extends GameState{

    private String prefix;
    private IngameTimer timer;
    private ItemManager itemManager;

    public IngameState(ItemManager itemManager){
        prefix = HG.INSTANCE.PREFIX;
        timer = new IngameTimer();
        this.itemManager = itemManager;
    }


    @Override
    public void start() {
        Bukkit.broadcastMessage(prefix + "§aInvincibility is over. May the fighting begin!");
        timer.start();
        ScoutKit.speedPotionRunnable(itemManager);
    }

    @Override
    public void stop() {

    }

    public IngameTimer getTimer() {
        return timer;
    }
}
