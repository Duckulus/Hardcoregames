package de.amin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        e.setCancelled(true);
        Bukkit.broadcastMessage(ChatColor.GRAY + e.getPlayer().getDisplayName() + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + e.getMessage());
    }


}
