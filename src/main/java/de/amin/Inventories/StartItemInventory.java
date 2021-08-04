//Created by Duckulus on 11 Jul, 2021 

package de.amin.Inventories;

import de.amin.hardcoregames.HG;
import de.amin.kit.StartItems;
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

public class StartItemInventory implements InventoryProvider {

    private StartItems startItems = HG.INSTANCE.getStartItems();

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("myInventory")
            .provider(new StartItemInventory())
            .size(3, 9)
            .title(ChatColor.BLUE + "Start Item Selector")
            .manager(HG.INSTANCE.getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        Pagination pagination = contents.pagination();

        ClickableItem[] items = new ClickableItem[startItems.startItems.size()];

        for (int i = 0; i <startItems.startItems.size() ; i++) {
            items[i] = ClickableItem.of(startItems.startItems.get(i), e -> {
                startItems.setStartItem(player, e.getCurrentItem());
                player.closeInventory();
            });
        }

        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)));

        pagination.setItems(items);
        pagination.setItemsPerPage(7);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).allowOverride(false));

        contents.set(2, 5, ClickableItem.of(new ItemBuilder(new ItemStack(Material.ARROW)).setDisplayName("§aNext Page").getItem(),
                e -> INVENTORY.open(player, pagination.next().getPage())));

        contents.set(2, 3, ClickableItem.of(new ItemBuilder(new ItemStack(Material.ARROW)).setDisplayName("§cPrevious Page").getItem(),
                e -> INVENTORY.open(player, pagination.previous().getPage())));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
