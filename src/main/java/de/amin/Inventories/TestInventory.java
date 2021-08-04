//Created by Duckulus on 05 Jul, 2021 

package de.amin.Inventories;

import de.amin.hardcoregames.HG;
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

import java.util.ArrayList;

public class TestInventory implements InventoryProvider {

    private Pagination pagination;

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("myInventory")
            .provider(new TestInventory())
            .size(6, 9)
            .title(ChatColor.BLUE + "Test")
            .manager(HG.INSTANCE.getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        pagination = contents.pagination();
        ClickableItem[] items = new ClickableItem[Material.values().length];

        for (int i = 0; i <Material.values().length ; i++) {
            items[i] = ClickableItem.of(new ItemStack(Material.values()[i]),
                    e -> player.sendMessage(e.getCurrentItem().getType().name()));
        }

        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 15)));

        ArrayList<String> progress = new ArrayList<>();
        progress.add(pagination.getPage() + "/" + pagination.getPage());

        contents.fillRect(2, 0, 3, 0, ClickableItem.of(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14)).setDisplayName(("§cprevious page")).setLore(progress).getItem(),
                e -> INVENTORY.open(player, pagination.previous().getPage())));


        contents.fillRect(2, 8, 3, 8, ClickableItem.of(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5)).setDisplayName("§anext page").setLore(progress).getItem(),
                e -> INVENTORY.open(player, pagination.next().getPage())));

        pagination.setItems(items);
        pagination.setItemsPerPage(28);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).allowOverride(false));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
