package de.amin.commands;

import de.amin.gamestates.GameStateManager;
import de.amin.gamestates.InvincibilityState;
import de.amin.gamestates.LobbyState;
import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.kit.impl.NoneKit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class KitCommand implements CommandExecutor, TabCompleter {

    private HG hg;
    private KitManager km;
    private GameStateManager gsm;

    public KitCommand() {
        hg = HG.INSTANCE;
        km = hg.getKitManager();
        gsm = hg.getGameStateManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!(args.length == 1)) {
            player.sendMessage("§7Usage§8: §9/kit [Name]");
        }

        if (km.getForcedKit() != null) {
            player.sendMessage("§cYou can't use this because a kit has been forced.");
            return true;
        }

        String kit = args[0];

        if (gsm.getCurrentGameState() instanceof LobbyState) {
            setKit(player, args[0]);
        } else if (gsm.getCurrentGameState() instanceof InvincibilityState) {
            if (km.getKitHashMap().get(player.getName()) instanceof NoneKit) {
                setKit(player, args[0]);
                if (getKitByName(args[0]) != null) {
                    getKitByName(args[0]).giveItems(player);
                }
            } else {
                player.sendMessage("§cYou already selected a kit.");
            }
        } else {
            player.sendMessage("§cYou cannot Pick your kit at this stage.");
        }
        return false;
    }

    private Kit getKitByName(String name) {
        String kitName;
        for (Kit kit : km.getKitArray()) {
            if (kit.getName().equalsIgnoreCase(name)) {
                kitName = kit.getName();
                return kit;
            }
        }
        return null;
    }

    private void setKit(Player player, String kit) {
        if (getKitByName(kit) == null) {
            player.sendMessage("§cKit not found");
        } else {
            km.setKit(player.getName(), getKitByName(kit));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        final ArrayList<String> completions = new ArrayList<>();
        final ArrayList<String> kits = new ArrayList<>();
        for (Kit k : km.getKitArray()) {
            kits.add(k.getName());
        }
        StringUtil.copyPartialMatches(args[0], kits, completions);
        return completions;
    }
}


