package de.amin.gamestates;

import de.amin.MySQL.Stats;
import de.amin.hardcoregames.HG;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;


public class EndingState extends GameState {

    private int count = 0;

    @Override
    public void start() {
        if (HG.INSTANCE.getPlayers().size() == 1) {
            for (Player p : HG.INSTANCE.getPlayers()) {
                p.setVelocity(new Vector(0, 2, 0));
                //Stats.incrementWins(p.getUniqueId().toString());

                Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, () -> {
                    if (count < 5) {
                        p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                        Bukkit.broadcastMessage("§a" + p.getName() + " WON!");
                    } else {
                        switch (count) {
                            case 5:
                                Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "The Server will close in 10 seconds");
                                break;
                            case 12:
                                Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "The Server will close in 3 seconds");
                                for (Player p1 : Bukkit.getOnlinePlayers()) {
                                    p1.playSound(p1.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                                }
                                break;
                            case 13:
                                Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "The Server will close in 2 seconds");
                                for (Player p1 : Bukkit.getOnlinePlayers()) {
                                    p1.playSound(p1.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                                }
                                break;
                            case 14:
                                Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "The Server will close in 1 seconds");
                                for (Player p1 : Bukkit.getOnlinePlayers()) {
                                    p1.playSound(p1.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                                }
                                break;
                            case 15:
                                HG.INSTANCE.getGameStateManager().stopCurrentGameState();
                                break;
                            default:
                                break;

                        }
                    }
                    count++;
                }, 10L, 20L);

            }
        } else {
            Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "§7There is no winner, because the game ended and more than one Player is alive!");
            count = 10;
            Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
                @Override
                public void run() {
                    switch (count) {
                        case 10:
                        case 9:
                        case 8:
                        case 7:
                        case 6:
                        case 5:
                        case 4:
                        case 3:
                        case 2:
                        case 1:
                            Bukkit.broadcastMessage(HG.INSTANCE.PREFIX + "The Server will close in " + count + "seconds");
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                            }
                            break;
                        case 0:
                            HG.INSTANCE.getGameStateManager().stopCurrentGameState();
                            break;
                        default:
                            break;
                    }
                    count--;
                }
            }, 0L, 20L);
        }
    }

    @Override
    public void stop() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.kickPlayer("§8Server Restarting");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "stop");
            }
        }, 20L);

    }


}
