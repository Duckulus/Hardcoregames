package de.amin.mechanics;

import de.amin.Feast.Bonusfeast;
import de.amin.Feast.Feast;
import de.amin.gamestates.*;
import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Scoreboards {

    private static ScoreboardManager manager = Bukkit.getScoreboardManager();
    private static GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();
    private static String ip = "§ihardcoregames.hg";

    public static void pregameScoreboard(Player player){
        Scoreboard scoreboard = (Scoreboard) manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("pregame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.RED + ChatColor.BOLD.toString() + "Starting in").setScore(10);
        if(gameStateManager.getCurrentGameState() instanceof LobbyState) {
            objective.getScore(" " + (formatTime(((LobbyState) gameStateManager.getCurrentGameState()).getCountdown().getSeconds()))).setScore(9);
        }
        objective.getScore("    ").setScore(8);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(7);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(6);
        objective.getScore("  ").setScore(5);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(4);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayers().size()).setScore(3);
        objective.getScore(" ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static void invincibilityScoreboard(Player player){
        Scoreboard scoreboard = (Scoreboard) manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("invincibility", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Invincible for").setScore(10);
        if(gameStateManager.getCurrentGameState() instanceof InvincibilityState) {
            objective.getScore(" " + (formatTime(((InvincibilityState) gameStateManager.getCurrentGameState()).getCountdown().getSeconds()))).setScore(9);
        }
        objective.getScore("    ").setScore(8);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(7);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(6);
        objective.getScore("  ").setScore(5);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(4);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayersAtStart()).setScore(3);
        objective.getScore(" ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static void ingameScoreboard(Player player){
        Scoreboard scoreboard = (Scoreboard) manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("ingame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "Elapsed Time").setScore(10);
        if(gameStateManager.getCurrentGameState() instanceof IngameState) {
            objective.getScore(" " + (formatTime(((IngameState) gameStateManager.getCurrentGameState()).getTimer().getSeconds()))).setScore(9);
        }
        objective.getScore("    ").setScore(8);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(7);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(6);
        objective.getScore("  ").setScore(5);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(4);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayersAtStart()).setScore(3);
        objective.getScore(" ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static void feastAnnouncedScoreboard(Player player){
        Scoreboard scoreboard = manager.getNewScoreboard();
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();

        Objective objective = scoreboard.registerNewObjective("ingame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "Elapsed Time").setScore(14);
        if(gameStateManager.getCurrentGameState() instanceof IngameState) {
            objective.getScore(" " + (formatTime(((IngameState) gameStateManager.getCurrentGameState()).getTimer().getSeconds()))).setScore(13);
        }
        objective.getScore("    ").setScore(12);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(11);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(10);
        objective.getScore("  ").setScore(9);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(8);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayersAtStart()).setScore(7);
        objective.getScore(" ").setScore(6);
        objective.getScore("§6§lFeast").setScore(5);
        objective.getScore("§f  at [" + feast.getxPos() + ", " + feast.getyPos() + ", " + feast.getzPos() + "]").setScore(4);
        objective.getScore("§f  in " + formatTime(feast.getSecondsToFeast())).setScore(3);
        objective.getScore("      ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static void feastSpawnedScoreboard(Player player){
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        Scoreboard scoreboard = manager.getNewScoreboard();
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();

        Objective objective = scoreboard.registerNewObjective("ingame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "Elapsed Time").setScore(14);
        if(gameStateManager.getCurrentGameState() instanceof IngameState) {
            objective.getScore(" " + (formatTime(((IngameState) gameStateManager.getCurrentGameState()).getTimer().getSeconds()))).setScore(13);
        }
        objective.getScore("    ").setScore(12);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(11);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(10);
        objective.getScore("  ").setScore(9);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(8);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayersAtStart()).setScore(7);
        objective.getScore(" ").setScore(6);
        objective.getScore("§6§lFeast").setScore(5);
        objective.getScore("§f  at [" + feast.getxPos() + ", " + feast.getyPos() + ", " + feast.getzPos() + "]").setScore(4);
        objective.getScore("§f  has begun!").setScore(3);
        objective.getScore("      ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static void pitScoreboard(Player player){
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        int seconds = ((IngameState)gameStateManager.getCurrentGameState()).getTimer().getSeconds();
        int gameEnd = ((IngameState)gameStateManager.getCurrentGameState()).getTimer().getGameEndTime();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Feast feast = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getFeast();

        Objective objective = scoreboard.registerNewObjective("ingame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "Elapsed Time").setScore(14);
        if(gameStateManager.getCurrentGameState() instanceof IngameState) {
            objective.getScore(" " + (formatTime(((IngameState) gameStateManager.getCurrentGameState()).getTimer().getSeconds()))).setScore(13);
        }
        objective.getScore("    ").setScore(11);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(10);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(9);
        objective.getScore("  ").setScore(8);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(7);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayersAtStart()).setScore(6);
        objective.getScore(" ").setScore(5);
        objective.getScore("§6§lGame End").setScore(4);
        objective.getScore("§f  in " + formatTime(gameEnd-seconds)).setScore(3);
        objective.getScore("      ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static void pitAnnouncedScoreboard(Player player){
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        int seconds = ((IngameState)gameStateManager.getCurrentGameState()).getTimer().getSeconds();
        int gameEnd = ((IngameState)gameStateManager.getCurrentGameState()).getTimer().getGameEndTime();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Pit pit = ((IngameState) HG.INSTANCE.getGameStateManager().getCurrentGameState()).getTimer().getPit();
        int timeToPit = (((IngameState)gameStateManager.getCurrentGameState()).getTimer().getPitTime()) - seconds + 5*60;

        Objective objective = scoreboard.registerNewObjective("ingame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "Elapsed Time").setScore(14);
        if(gameStateManager.getCurrentGameState() instanceof IngameState) {
            objective.getScore(" " + (formatTime(((IngameState) gameStateManager.getCurrentGameState()).getTimer().getSeconds()))).setScore(13);
        }
        objective.getScore("    ").setScore(11);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(10);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(9);
        objective.getScore("  ").setScore(8);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(7);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayersAtStart()).setScore(6);
        objective.getScore(" ").setScore(5);
        objective.getScore("§6§lPit").setScore(4);
        objective.getScore("§f  in " + formatTime(timeToPit)).setScore(3);
        objective.getScore("      ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static void bonusFeastScoreboard(Player player){
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        int seconds = ((IngameState)gameStateManager.getCurrentGameState()).getTimer().getSeconds();
        Bonusfeast bonusfeast = ((IngameState)gameStateManager.getCurrentGameState()).getTimer().getBonusFeast();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("ingame", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.WHITE + ChatColor.BOLD.toString() + "HardcoreGames");
        objective.getScore(ChatColor.GREEN + ChatColor.BOLD.toString() + "Elapsed Time").setScore(14);
        if(gameStateManager.getCurrentGameState() instanceof IngameState) {
            objective.getScore(" " + (formatTime(seconds))).setScore(13);
        }
        objective.getScore("    ").setScore(11);
        objective.getScore("§9§lKit:§r §f" + HG.INSTANCE.getKitManager().getKitHashMap().get(player.getName()).getName()).setScore(10);
        objective.getScore("§b§lKills:§r §f" + HG.INSTANCE.getKills().get(player.getName())).setScore(9);
        objective.getScore("  ").setScore(8);
        objective.getScore(ChatColor.DARK_PURPLE + ChatColor.BOLD.toString() + "Players").setScore(7);
        objective.getScore(" §f" + HG.INSTANCE.getPlayers().size() + "/"  + HG.INSTANCE.getPlayersAtStart()).setScore(6);
        objective.getScore(" ").setScore(5);
        objective.getScore("§6§lBonusfeast").setScore(4);
        objective.getScore("§f  in " + bonusfeast.getBiome().toString()).setScore(3);
        objective.getScore("      ").setScore(2);
        objective.getScore(ip).setScore(1);
        player.setScoreboard(scoreboard);
    }

    public static String formatTime(int seconds){
            if(seconds <= 0)return "";
            int m = seconds / 60;
            int s = seconds % 60;
            String formattedM;
            String formattedS;
            if(m>99){
                formattedM = String.format("%03d", m);
            }else {
                formattedM = String.format("%02d", m);
            }
        formattedS = String.format("%02d", s);

        return formattedM+":"+formattedS;
        }
}


