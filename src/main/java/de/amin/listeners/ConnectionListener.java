package de.amin.listeners;

import de.amin.countdowns.LobbyCountdown;
import de.amin.gamestates.GameState;
import de.amin.gamestates.IngameState;
import de.amin.gamestates.InvincibilityState;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import de.amin.kit.KitManager;
import de.amin.kit.KitSelector;
import de.amin.kit.StartItems;
import de.amin.kit.impl.NoneKit;
import de.amin.managers.VanishManager;
import de.amin.mechanics.AdminMode;
import de.amin.mechanics.RandomTP;
import de.amin.mechanics.Scoreboards;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class ConnectionListener implements Listener {

    private final VanishManager vanishManager;
    private final ArrayList<String> kickedPlayers;

    public ConnectionListener() {
        vanishManager = HG.INSTANCE.getVanishManager();
        kickedPlayers = new ArrayList<>();
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        HG plugin = HG.INSTANCE;
        KitManager kitManager = HG.INSTANCE.getKitManager();
        if (HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState) {

            //vanish vanished players
            for (String s : vanishManager.getVanishedPlayers()) {
                e.getPlayer().hidePlayer(Bukkit.getPlayer(s));
            }



            e.getPlayer().getInventory().clear();
            giveItems(e.getPlayer());
            Location spawn = new Location(Bukkit.getWorld("world"), 0, 0, 0);
            e.getPlayer().teleport(getHighestPoint(spawn));


            if(kitManager.getForcedKit()==null){
                kitManager.setKit(e.getPlayer().getName(), kitManager.getKitByName("none"));

            }else{
                kitManager.setKit(e.getPlayer().getName(), kitManager.getForcedKit());
            }




            HG.INSTANCE.getPlayers().add(e.getPlayer());
            HG.INSTANCE.getKills().put(e.getPlayer().getName(), 0);
            Scoreboards.pregameScoreboard(e.getPlayer());

            LobbyState lobbyState = (LobbyState) plugin.getGameStateManager().getCurrentGameState();
            LobbyCountdown countdown = lobbyState.getCountdown();
            if (plugin.getPlayers().size() >= LobbyState.MIN_PLAYERS) {
                if (!countdown.isRunning()) {
                    countdown.stopIdle();
                    countdown.start();
                }
            }
        } else {
            e.setJoinMessage(null);
            if(!e.getPlayer().isOp()){
                HG.INSTANCE.getSpecMode().activate(e.getPlayer());
            }else {
                HG.INSTANCE.getAdminMode().activate(e.getPlayer());
            }

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        HG plugin = HG.INSTANCE;
        KitManager kitManager = HG.INSTANCE.getKitManager();
        AdminMode adminMode = HG.INSTANCE.getAdminMode();

        if (vanishManager.isVanished(e.getPlayer())) {
            vanishManager.unvanish(e.getPlayer());
        }

        if (adminMode.isAdminMode(e.getPlayer())) {
            adminMode.deactivate(e.getPlayer());
        }

        if (HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState) {
            HG.INSTANCE.getPlayers().remove(e.getPlayer());
            plugin.getPlayers().remove(e.getPlayer());
            kitManager.getKitHashMap().remove(e.getPlayer().getName());


            LobbyState lobbyState = (LobbyState) plugin.getGameStateManager().getCurrentGameState();
            LobbyCountdown countdown = lobbyState.getCountdown();
            if (plugin.getPlayers().size() < LobbyState.MIN_PLAYERS) {
                if (countdown.isRunning()) {
                    countdown.stop();
                    countdown.startIdle();
                }
            }
        } else if (plugin.getGameStateManager().getCurrentGameState() instanceof IngameState || plugin.getGameStateManager().getCurrentGameState() instanceof InvincibilityState) {
            plugin.getPlayers().remove(e.getPlayer());
            if (plugin.getPlayers().size() <= 1) {
                if(!kickedPlayers.contains(e.getPlayer().getName())) {
                    System.out.println(e.getQuitMessage());
                    plugin.getGameStateManager().setGameState(GameState.ENDING_STATE);
                }
            }
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        HG plugin = HG.INSTANCE;

        if (plugin.getGameStateManager().getCurrentGameState() instanceof IngameState || plugin.getGameStateManager().getCurrentGameState() instanceof InvincibilityState) {
            plugin.getPlayers().remove(e.getPlayer());
            if (plugin.getPlayers().size() == 1) {
                kickedPlayers.add(e.getPlayer().getName());
                plugin.getGameStateManager().setGameState(GameState.ENDING_STATE);
            }
        }
    }

    public static Location getHighestPoint(Location loc) {
        for (int i = 256; i > 0; i--) {
            loc.setY(i);
            if (!loc.getBlock().getType().equals(Material.AIR)) {
                loc.setY(loc.getY() + 1);
                return loc;
            }
        }
        return loc;
    }

    private void giveItems(Player player){
        player.getInventory().setItem(0, new ItemBuilder(new ItemStack(Material.CHEST)).setDisplayName(KitSelector.DISPLAY_NAME).getItem());
        player.getInventory().setItem(1, new ItemBuilder(new ItemStack(Material.ENDER_CHEST)).setDisplayName(StartItems.DISPLAY_NAME).getItem());
        player.getInventory().setItem(8, new ItemBuilder(new ItemStack(Material.BLAZE_ROD)).setDisplayName(RandomTP.ITEM_NAME).getItem());
    }


}
