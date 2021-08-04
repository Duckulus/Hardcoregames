package de.amin.countdowns;

import de.amin.gamestates.GameState;
import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import de.amin.mechanics.RandomTP;
import de.amin.mechanics.Scoreboards;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LobbyCountdown extends Countdown {

    private FileConfiguration config = HG.INSTANCE.getFileConfig();

    private final int IDLE_TIME = 15,
            COUNTDOWN_TIME = (int) config.get("timers.pregame");


    private GameStateManager gameStateManager;

    private boolean isIdling;
    private boolean isRunning;
    private int idleID;
    private int seconds;

    public LobbyCountdown(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        seconds = COUNTDOWN_TIME;
    }


    @Override
    public void start() {
        String prefix = HG.INSTANCE.PREFIX;
        isRunning = true;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {

            @Override
            public void run() {
                switch (seconds) {
                    case 60:
                    case 30:
                    case 15:
                        Bukkit.broadcastMessage(prefix + "§7The game will start in §9" + seconds + " seconds§7.");
                        break;
                    case 10:
                        Bukkit.broadcastMessage(prefix + "§7The game will start in §9" + seconds + " seconds§7.");
                        for (Player p : HG.INSTANCE.getPlayers()) {
                            p.getInventory().setItem(8, new ItemStack(Material.AIR));
                            p.teleport(RandomTP.getHighestPoint(new Location(p.getWorld(), 0.0D, 0.0D, 0.0D)));
                            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);

                        }
                        break;
                    case 5:
                    case 4:
                    case 3:
                    case 2:
                        Bukkit.broadcastMessage(prefix + "§7The game will start in §9" + seconds + " seconds§7.");
                        for (Player p : HG.INSTANCE.getPlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        }
                        break;
                    case 1:
                        Bukkit.broadcastMessage(prefix + "§7The game will start in §9one second§7.");
                        for (Player p : HG.INSTANCE.getPlayers()) {
                            p.playSound(p.getLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                        }
                        break;
                    case 0:
                        gameStateManager.setGameState(GameState.INVINCIBILITY_STATE);
                        break;
                    default:
                        break;
                }
                for (Player p : Bukkit.getOnlinePlayers()) {
                    Scoreboards.pregameScoreboard(p);
                }
                seconds--;

            }
        }, 0, 20);
    }

    @Override
    public void stop() {
        if (isRunning) {
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            seconds = COUNTDOWN_TIME;
        }
    }

    public void startIdle() {
        String prefix = HG.INSTANCE.PREFIX;
        isIdling = true;
        idleID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                int requiredPlayers = LobbyState.MIN_PLAYERS - HG.INSTANCE.getPlayers().size();
                if (requiredPlayers == 1) {
                    Bukkit.broadcastMessage(prefix + ChatColor.YELLOW + "1 more player §crequired to start.");
                } else {
                    Bukkit.broadcastMessage(prefix + ChatColor.YELLOW + requiredPlayers + " more players§c required to start.");
                }
            }
        }, 10 * 20, IDLE_TIME * 20);
    }

    public void stopIdle() {
        if (isIdling) {
            Bukkit.getScheduler().cancelTask(idleID);
            isIdling = false;
        }
    }

    public boolean isRunning() {
        return isRunning;
    }


    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public int getSeconds() {
        return seconds;
    }
}
