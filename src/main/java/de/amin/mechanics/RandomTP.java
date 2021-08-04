package de.amin.mechanics;

import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTP implements Listener {

    public static final String ITEM_NAME = "§6Random Teleport";

    public static void randomTeleport(Player p){
        int bordersize = (int) HG.INSTANCE.getFileConfig().get("mechanics.bordersize");
        int x = ThreadLocalRandom.current().nextInt(bordersize * -1,bordersize);
        int z = ThreadLocalRandom.current().nextInt(bordersize * -1,bordersize);

        Location randomLoc = new Location(p.getWorld(), x, 0, z);
        Location loc = getHighestPoint(randomLoc);
        loc.setY(loc.getY() + 5);
        p.teleport(loc);

    }

    public static Location getHighestPoint(Location loc){
        for(int i = 256; i>0; i--){
            loc.setY(i);
            if(!loc.getBlock().getType().equals(Material.AIR)){
                loc.setY(loc.getY() + 1);
                return loc;
            }
        }
        return loc;
    }



    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(!(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof LobbyState))return;
        if(e.getItem()==null)return;
        if(!e.getItem().hasItemMeta())return;
        if(!e.getItem().getItemMeta().getDisplayName(). equals(ITEM_NAME)){
            return;
        }
        randomTeleport(e.getPlayer());
    }
}
