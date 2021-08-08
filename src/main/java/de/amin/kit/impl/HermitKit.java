//Created by Duckulus on 05 Aug, 2021 

package de.amin.kit.impl;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.IngameState;
import de.amin.gamestates.InvincibilityState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.KitSetting;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class HermitKit extends Kit implements Listener, CommandExecutor {

    public static HashMap<String, Location> hermitPlayers = new HashMap<>();

    private static int taskID = 0;
    private final GameStateManager gameStateManager = HG.INSTANCE.getGameStateManager();
    private final KitManager kitManager = HG.INSTANCE.getKitManager();

    public HermitKit() {

    }

    @Override
    public void giveItems(Player p) {

    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.BROWN_MUSHROOM);
    }

    @Override
    public String getName() {
        return "Hermit";
    }

    @Override
    public ArrayList<String> getDescription() {
        ArrayList<String> description = new ArrayList<>();
        description.add("§7Use /setspawn before the round starts to");
        description.add("§7choose where you spawn. You also gain special");
        description.add("§7effects during invincibility and after farming");
        description.add("§7Mushrooms");
        return description;
    }

    @Override
    public KitSetting[] getKitSettings() {
        return new KitSetting[0];
    }

    public static void run(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(HG.INSTANCE, new Runnable() {
            @Override
            public void run() {
                if(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof IngameState)Bukkit.getScheduler().cancelTask(taskID);
                if(HG.INSTANCE.getGameStateManager().getCurrentGameState() instanceof InvincibilityState){
                    for (Player player : HG.INSTANCE.getPlayers()){
                        if(HG.INSTANCE.getKitManager().getKit(player) instanceof HermitKit){
                            renewEffect(player, new PotionEffect(PotionEffectType.SPEED, 100, 2));
                        }
                    }
                }
            }
        },100,40);
    }

    @EventHandler
    public void onMushroomBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(!(kitManager.getKit(player) instanceof HermitKit))return;
        if(!(gameStateManager.getCurrentGameState() instanceof IngameState))return;
        if(!event.getBlock().getType().name().toLowerCase().contains("mushroom"))return;
        renewEffect(player, new PotionEffect(PotionEffectType.SPEED, 40, 1));
        renewEffect(player, new PotionEffect(PotionEffectType.REGENERATION, 40, 1));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))return true;
        Player player = (Player) sender;
        if(!(kitManager.getKit(player) instanceof HermitKit)){
            player.sendMessage("§cYou need to be Hermit to use this!");
            return true;
        }
        hermitPlayers.put(player.getName(), player.getLocation());
        player.sendMessage(HG.INSTANCE.PREFIX + "§7Succesfully set spawn location!");
        return false;
    }

    private static void renewEffect(Player player, PotionEffect effect){
        player.removePotionEffect(effect.getType());
        player.addPotionEffect(effect);
    }
}
