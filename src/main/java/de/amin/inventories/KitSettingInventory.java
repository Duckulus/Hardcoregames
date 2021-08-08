//Created by Duckulus on 04 Aug, 2021 

package de.amin.inventories;

import de.amin.hardcoregames.HG;
import de.amin.kit.Kit;
import de.amin.kit.KitSetting;
import de.amin.utils.AnvilGUI;
import de.amin.utils.ItemBuilder;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KitSettingInventory implements InventoryProvider {

    private Kit kit;

    public KitSettingInventory(Kit kit){
        this.kit = kit;
    }

    public SmartInventory getInventory() {
        return SmartInventory.builder()
                .id("myInventory")
                .provider(new KitSettingInventory(kit))
                .size(3, 9)
                .title(ChatColor.BLUE + "Kitsettings for " + kit.getName())
                .manager(HG.INSTANCE.getInventoryManager())
                .build();
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.fillBorders(ClickableItem.empty(new ItemStack(Material.STAINED_GLASS_PANE)));
        Pagination pagination = contents.pagination();

        ClickableItem[] items = new ClickableItem[kit.getKitSettings().length];


        int i = 0;
        for (KitSetting setting : kit.getKitSettings()) {
            ArrayList<String> lore = new ArrayList<>();
            lore.add("§7" + setting.getValue());
            lore.add("§7Click to change");
            lore.add("§7Shiftclick to restore default value");
            ItemStack item = new ItemBuilder(new ItemStack(Material.COMMAND)).setDisplayName("§b" + setting.getName()).setLore(lore).getItem();

            items[i] = ClickableItem.of(item, e -> {
                if(e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)){
                    setting.restoreDefault();
                    player.closeInventory();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, () -> {
                        new KitSettingInventory(kit).getInventory().open(player);
                    }, 1);
                }else {
                    new AnvilGUI(HG.INSTANCE, player, new AnvilGUI.AnvilClickHandler() {
                        @Override
                        public boolean onClick(AnvilGUI menu, String text){
                            double value = 0;

                            try {
                                value = Double.parseDouble(text);
                            } catch (NumberFormatException exception) {
                                player.sendMessage("§cInvalid Number format!");

                                return true;
                            }

                            if(value > setting.getMaxValue() || value < setting.getMinValue()){
                                player.sendMessage("§cValue out of range for setting "
                                        + setting.getName() + "(" + setting.getMinValue() + "-" + setting.getMaxValue() + ")");
                            }else {
                                setting.setValue(value);
                                player.sendMessage("§aSuccesfully changed value for Setting §6" + setting.getName() + " §ato §6" + value + "§a!");
                            }
                            Bukkit.getScheduler().scheduleSyncDelayedTask(HG.INSTANCE, () -> {
                                new KitSettingInventory(kit).getInventory().open(player);
                            }, 1);
                            return true;
                        }
                    }).setInputName("Enter new Value!").open();
                }


            });

            i++;
        }

        pagination.setItems(items);
        pagination.setItemsPerPage(7);

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));

        contents.set(0,0, ClickableItem.of(new ItemBuilder(new ItemStack(Material.WOOL,1, (short) 14)).setDisplayName("§cGo Back").getItem(), e -> {
            KitSettingListInventory.INVENTORY.open(player);
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {

    }
}
