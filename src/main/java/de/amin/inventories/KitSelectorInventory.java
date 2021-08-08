//Created by Duckulus on 04 Jul, 2021 

package de.amin.inventories;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitManager;
import de.amin.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitSelectorInventory implements InventoryProvider {

    private final KitManager kitManager = HG.INSTANCE.getKitManager();
    private Pagination pagination;

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("myInventory")
            .provider(new KitSelectorInventory())
            .size(6, 9)
            .title(ChatColor.BLUE + "Kit Selector")
            .manager(HG.INSTANCE.getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        pagination = contents.pagination();

        ClickableItem[] items = new ClickableItem[kitManager.getKitArray().size()];

        int i = 0;

        for (Kit k : kitManager.getKitArray()){
            ItemStack kitItem = k.getItem();
            ItemMeta meta = k.getItem().getItemMeta();
            meta.setDisplayName("§a" + k.getName());
            meta.setLore(k.getDescription());
            kitItem.setItemMeta(meta);

            if(kitManager.isEnabled(k)){
                items[i] = (ClickableItem.of(kitItem,
                        e -> kitManager.setKit(player.getName(), k)));
                i++;
            }
        }


        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1)));


        pagination.setItems(items);
        pagination.setItemsPerPage(28);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).allowOverride(false));


        contents.fillRect(2, 0, 3, 0, ClickableItem.of(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14)).setDisplayName(("§cprevious page")).getItem(),
                e -> INVENTORY.open(player, pagination.previous().getPage())));


        contents.fillRect(2, 8, 3, 8, ClickableItem.of(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5)).setDisplayName("§anext page").getItem(),
                e -> INVENTORY.open(player, pagination.next().getPage())));


        contents.set(0, 3, ClickableItem.of(new ItemBuilder(new ItemStack(Material.NETHER_STAR)).setDisplayName("§bRandom Kit").getItem(),
                e -> kitManager.selectRandomKit(player)));

        contents.set(0, 5, ClickableItem.of(new ItemBuilder(new ItemStack(Material.COMPASS)).setDisplayName("§bSearch Kit").getItem(),
                e -> kitManager.promtKitSearch(player)));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }


}
