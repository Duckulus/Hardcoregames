//Created by Duckulus on 01 Jul, 2021 

package de.amin.mechanics;

import de.amin.hardcoregames.HG;
import de.amin.managers.VanishManager;
import de.amin.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class AdminUtils implements Listener {

    private AdminMode adminMode;
    private VanishManager vanishManager;

    private final ItemStack players;
    private final ItemStack playerInfo;
    private final ItemStack invSee;
    private final ItemStack vanishOn;
    private final ItemStack vanishOff;
    private final ItemStack teleport;

    public AdminUtils(){
        adminMode = HG.INSTANCE.getAdminMode();
        vanishManager = HG.INSTANCE.getVanishManager();

        players = new ItemBuilder(new ItemStack(Material.COMPASS)).setDisplayName("§ePlayers").getItem();
        playerInfo = new ItemBuilder(new ItemStack(Material.SIGN)).setDisplayName("§cPlayer Info").getItem();
        invSee = new ItemBuilder(new ItemStack(Material.CHEST)).setDisplayName("§aOpen Inventory").getItem();
        vanishOn = new ItemBuilder(new ItemStack(Material.BLAZE_ROD)).setDisplayName("§7Vanish Mode: §aon").getItem();
        vanishOff = new ItemBuilder(new ItemStack(Material.BLAZE_POWDER)).setDisplayName("§7Vanish Mode: §coff").getItem();
        teleport = new ItemBuilder(new ItemStack(Material.REDSTONE_TORCH_ON)).setDisplayName("§7teleport").getItem();
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        if(!(e.getRightClicked() instanceof Player))return;
        Player player = e.getPlayer();
        if (!HG.INSTANCE.getAdminMode().getAdminPlayers().containsKey(player.getName())) return;
        Player target = (Player) e.getRightClicked();

        switch (player.getItemInHand().getType()) {
            case CHEST:
                player.performCommand("invsee " + target.getName());
                break;
            case SIGN:
                playerInfo(player, target);
                break;
            default:
                break;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            Player player = e.getPlayer();
            if (!adminMode.isAdminMode(player)) return;
            if (!e.hasItem()) return;
            switch (e.getItem().getType()) {
                case COMPASS:
                    openPlayerList(player);
                    break;
                case BLAZE_POWDER:
                    player.setItemInHand(vanishOn);
                    vanishManager.vanish(player);
                    break;
                case BLAZE_ROD:
                    player.setItemInHand(vanishOff);
                    vanishManager.unvanish(player);
                    break;
                case REDSTONE_TORCH_ON:
                    player.teleport(player.getLocation().add(player.getLocation().getDirection().normalize().multiply(10.0)));
                    break;
                default:
                    break;
            }
        }
    }




    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("§bPlayers")) return;
        e.setCancelled(true);
        Player player = (Player) e.getWhoClicked();
        Player target = Bukkit.getPlayer(e.getCurrentItem().getItemMeta().getDisplayName().replace("§a", ""));

        if (target == null) {
            player.sendMessage("§cPlayer not found");
        } else {
            player.teleport(target.getLocation());
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(adminMode.getAdminPlayers().containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof Player){
            if(adminMode.getAdminPlayers().containsKey(e.getEntity().getName())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        if(adminMode.getAdminPlayers().containsKey(e.getPlayer().getName())){
            e.setCancelled(true);
        }
    }

    private void openPlayerList(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "§bPlayers");
        for (Player p : Bukkit.getOnlinePlayers()) {
            ItemStack skull = new ItemBuilder(new ItemStack(Material.SKULL_ITEM, 1, (short) 3)).setDisplayName("§a" + p.getName()).getItem();
            SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
            skullMeta.setOwner(p.getName());
            skull.setItemMeta(skullMeta);
            inventory.addItem(skull);
        }
        player.openInventory(inventory);
    }

    private void playerInfo(Player player, Player target) {
        player.sendMessage("§b§m--------------------------------");
        player.sendMessage("§e§l           Player Info            ");
        player.sendMessage("");
        player.sendMessage("§7Name§8: §9" + target.getName());
        player.sendMessage("§7Ping§8: §9" + ((CraftPlayer) target).getHandle().ping);
        player.sendMessage("§7Health§8: §9" + target.getHealth() / 2 + " Hearts");
        player.sendMessage("§7IP-Adress§8: §9" + target.getAddress());
        player.sendMessage("§7Kills§8: §9" + HG.INSTANCE.getKills().get(target.getName()));
        player.sendMessage("§7Kit§8: §9" + HG.INSTANCE.getKitManager().getKitHashMap().get(target.getName()).getName());
        player.sendMessage("§b§m--------------------------------");

    }

}
