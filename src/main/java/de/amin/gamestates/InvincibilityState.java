package de.amin.gamestates;

import de.amin.countdowns.InvincibilityCountdown;
import de.amin.hardcoregames.HG;
import de.amin.kit.impl.HermitKit;
import de.amin.kit.KitManager;
import de.amin.kit.impl.SurpriseKit;
import de.amin.mechanics.RandomTP;
import de.amin.mechanics.Scoreboards;
import de.amin.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class InvincibilityState extends GameState{

    private final InvincibilityCountdown countdown;
    private final KitManager kitManager;

    public InvincibilityState(GameStateManager gameStateManager, KitManager kitManager){
        countdown = new InvincibilityCountdown(gameStateManager);
        this.kitManager = kitManager;
    }

    @Override
    public void start() {
        countdown.start();
        HG.INSTANCE.setPlayersAtStart(HG.INSTANCE.getPlayers().size());
        HermitKit.run();
        for(Player p : HG.INSTANCE.getPlayers()){

            //give Player random kit if surprise
            if(kitManager.getKit(p) instanceof SurpriseKit){
                int random = new Random().nextInt(kitManager.getKitArray().size());
                kitManager.setKit(p.getName(), kitManager.getKitArray().get(random));
            }else if(kitManager.getKit(p) instanceof HermitKit) {
                if(HermitKit.hermitPlayers.containsKey(p.getName())){
                    p.teleport(RandomTP.getHighestPoint(HermitKit.hermitPlayers.get(p.getName())));
                }else {
                    RandomTP.randomTeleport(p);
                }
            }


            HG.INSTANCE.getStats().increment(p.getName(), "GAMESPLAYED");
            p.getInventory().clear();
            HG.INSTANCE.getKitManager().getKitHashMap().get(p.getName()).giveItems(p);
            HG.INSTANCE.getStartItems().giveItem(p);
            p.getInventory().setItem(8, new ItemBuilder(new ItemStack(Material.COMPASS)).setDisplayName("§c§lPlayer Tracker").getItem());
            Scoreboards.invincibilityScoreboard(p);
        }
    }

    @Override
    public void stop() {
        for(Player p : HG.INSTANCE.getPlayers()){
            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
        }
        countdown.stop();
    }

    public InvincibilityCountdown getCountdown() {
        return countdown;
    }
}
