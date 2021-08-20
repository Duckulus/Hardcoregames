package de.amin.countdowns;

import de.amin.feast.Bonusfeast;
import de.amin.feast.Feast;
import de.amin.feast.Minifeast;
import de.amin.gamestates.GameState;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import de.amin.mechanics.Pit;
import de.amin.mechanics.Scoreboards;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Random;

public class IngameTimer extends Countdown {

    private FileConfiguration config = HG.INSTANCE.getConfig();
    private int seconds;
    private int taskID;

    private final int feastTime = config.getInt("feast.time");
    private final int bonusFeastTime = config.getInt("bonusfeast.time");
    private final int pitTime = config.getInt("pit.time");
    private final int gameEndTime = config.getInt("gameend.time");

    private Feast feast;
    private Bonusfeast bonusfeast;
    private Pit pit;


    public IngameTimer() {
        seconds = config.getInt("timers.invincibility");
    }

    @Override
    public void start() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {

                if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState))return;

                for (Player p : Bukkit.getOnlinePlayers()) {


                        if (Pit.isPit) {
                            Scoreboards.pitScoreboard(p);
                        } else if (Pit.isPitAnnounced) {
                            Scoreboards.pitAnnouncedScoreboard(p);
                        } else if (Bonusfeast.isBonusFeast) {
                            Scoreboards.bonusFeastScoreboard(p);
                        } else if (Feast.isFeast()) {
                            Scoreboards.feastSpawnedScoreboard(p);
                        } else if (Feast.isAnnounced()) {
                            Scoreboards.feastAnnouncedScoreboard(p);
                        } else {
                            Scoreboards.ingameScoreboard(p);
                        }


                }

                if(seconds==feastTime){
                    if (!Feast.isAnnounced()) {
                        feast = new Feast();
                    }
                }else if(seconds == bonusFeastTime){
                    bonusfeast = new Bonusfeast();
                }else if(seconds==pitTime){
                    if(!Pit.isPitAnnounced){
                        pit = new Pit();
                    }
                }else if(seconds == gameEndTime){
                    HG.INSTANCE.getGameStateManager().setGameState(GameState.ENDING_STATE);
                    Bukkit.getScheduler().cancelTask(taskID);
                }

                //1 zu 200 Chance das ein Minifeast spawnt jede Sekunde
                if (new Random().nextInt(200) == 100 && seconds > 3 * 60 && seconds < 15 * 60) {
                    new Minifeast();
                }
                seconds++;
            }
        }, 0, 20L);
    }

    @Override
    public void stop() {

    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Feast getFeast() {
        return feast;
    }

    public void setFeast(Feast feast) {
        this.feast = feast;
    }

    public int getGameEndTime() {
        return gameEndTime;
    }

    public Pit getPit() {
        return pit;
    }

    public void setPit(Pit pit) {
        this.pit = pit;
    }

    public int getPitTime() {
        return pitTime;
    }

    public Bonusfeast getBonusFeast() {
        return bonusfeast;
    }
}


