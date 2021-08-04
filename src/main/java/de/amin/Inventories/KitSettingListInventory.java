//Created by Duckulus on 03 Aug, 2021 

package de.amin.Inventories;

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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class KitSettingListInventory implements InventoryProvider {

    private final KitManager kitManager = HG.INSTANCE.getKitManager();
    private Pagination pagination;

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("myInventory")
            .provider(new KitSettingListInventory())
            .size(6, 9)
            .title(ChatColor.BLUE + "Select Kit")
            .manager(HG.INSTANCE.getInventoryManager())
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        pagination = contents.pagination();

        ClickableItem[] items = new ClickableItem[kitManager.getKitArray().size()];

        for (int i = 0; i < kitManager.getKitArray().size(); i++) {
            Kit k = kitManager.getKitArray().get(i);
            ItemStack kitItem = k.getItem();
            ItemMeta meta = k.getItem().getItemMeta();
            meta.setDisplayName("§a" + k.getName());

            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7Leftclick to toggle");
            lore.add("§7Rightclick to open Settings");
            meta.setLore(lore);

            kitItem.setItemMeta(meta);

            items[i] = (ClickableItem.of(kitItem, e -> {
                if (e.getClick().equals(ClickType.LEFT)) {
                    kitManager.setEnabled(k.getName(), !kitManager.isEnabled(k), player);
                }else if (e.getClick().equals(ClickType.RIGHT)){
                    new KitSettingInventory(k).getInventory().open(player);
                }
            }));


        }

        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE, 1)));


        pagination.setItems(items);
        pagination.setItemsPerPage(28);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).allowOverride(false));

        contents.fillRect(2, 0, 3, 0, ClickableItem.of(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14)).setDisplayName(("§cprevious page")).getItem(),
                e -> INVENTORY.open(player, pagination.previous().getPage())));


        contents.fillRect(2, 8, 3, 8, ClickableItem.of(new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5)).setDisplayName("§anext page").getItem(),
                e -> INVENTORY.open(player, pagination.next().getPage())));


        contents.set(0, 0, ClickableItem.of(new ItemBuilder(new ItemStack(Material.WOOL, 1, (short) 5)).setDisplayName("§aEnable All").getItem(),
                e -> kitManager.enableAll()));

        contents.set(0, 8, ClickableItem.of(new ItemBuilder(new ItemStack(Material.WOOL,1 , (short) 14)).setDisplayName("§cDisable All").getItem(),
                e -> kitManager.disableAll()));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
