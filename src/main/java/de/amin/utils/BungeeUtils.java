package de.amin.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.amin.hardcoregames.HG;
import org.bukkit.entity.Player;

public class BungeeUtils {

    public static void sendPlayer(Player player, String server){
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("ConnectOther");
            out.writeUTF(player.getName());
            out.writeUTF(server);
            //Any online player at all.
            //Aka Pull To Server.
            player.sendPluginMessage(HG.INSTANCE, "BungeeCord", out.toByteArray());

    }
}


