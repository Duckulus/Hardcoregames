//Created by Duckulus on 05 Jul, 2021 

package de.amin.mechanics;

import de.amin.Feast.Feast;
import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.hardcoregames.HG;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RecraftNerf {

    public boolean nerfed;
    public int maxAmount;

    private int taskID;

    private FileConfiguration config;
    private GameStateManager gameStateManager;

    private PotionEffect weakness;


    public RecraftNerf(FileConfiguration config, GameStateManager gameStateManager) {
        this.config = config;
        this.gameStateManager = gameStateManager;
        nerfed = config.getBoolean("mechanics.recraftnerf.on");
        maxAmount = config.getInt("mechanics.recraftnerf.max");

        weakness = new PotionEffect(PotionEffectType.WEAKNESS, 6*20, 4);

        run();
    }

    private void run() {
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                if (gameStateManager.getCurrentGameState() instanceof IngameState) {
                    if (Feast.isAnnounced()) {
                        Bukkit.getScheduler().cancelTask(taskID);
                    }

                    nerfed = config.getBoolean("mechanics.recraftnerf.on");
                    maxAmount = config.getInt("mechanics.recraftnerf.max");
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        int redShroomAmount = 0;
                        int brownShroomAmount = 0;
                        int cocoAmount = 0;
                        int cactiAmount = 0;
                        int totalAmount;

                        for (int i = 0; i < player.getInventory().getSize(); i++) {
                            if (player.getInventory().getItem(i) != null) {
                                switch (player.getInventory().getItem(i).getType()) {
                                    case RED_MUSHROOM:
                                        redShroomAmount += player.getInventory().getItem(i).getAmount();
                                        break;
                                    case BROWN_MUSHROOM:
                                        brownShroomAmount += player.getInventory().getItem(i).getAmount();
                                        break;
                                    case INK_SACK:
                                        cocoAmount += player.getInventory().getItem(i).getAmount();
                                        break;
                                    case CACTUS:
                                        cactiAmount += player.getInventory().getItem(i).getAmount();
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }

                        if (redShroomAmount > brownShroomAmount) {
                            totalAmount = redShroomAmount + cocoAmount + cactiAmount;
                        } else {
                            totalAmount = brownShroomAmount + cocoAmount + cactiAmount;
                        }

                        if (totalAmount > maxAmount && nerfed) {
                            player.addPotionEffect(weakness);
                            player.sendMessage("§c§lYou are carrying too much recraft!");
                            player.sendMessage("§c§lYour bones feel weak!");
                        }


                    }
                }

            }
        }, 0, 60);
    }

}
