//Created by Duckulus on 04 Aug, 2021 

package de.amin.utils;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.inventory.Inventory;

import java.lang.reflect.Field;

/**
 * Created by chasechocolate.
 */
public class AnvilNMS {

    public static class AnvilContainer extends ContainerAnvil {
        private static Field fieldText;

        static {
            try{
                fieldText = ContainerAnvil.class.getDeclaredField("l");
                fieldText.setAccessible(true);
            } catch(NoSuchFieldException e){
                e.printStackTrace();
            }
        }

        private AnvilGUI menu;

        public AnvilContainer(EntityHuman human, AnvilGUI menu){
            super(human.inventory, human.world, new BlockPosition(0, 0, 0), human);

            this.menu = menu;
        }

        /**
         * No EXP required to use
         */
        @Override
        public boolean a(EntityHuman human){
            return true;
        }

        @Override
        public void a(String text){
            menu.itemName = text == null ? "" : text;
            super.a(text);
        }
    }

    private static final ChatMessage PACKET_MESSAGE = new ChatMessage(Blocks.ANVIL.a() + ".name");

    public static Inventory open(AnvilGUI menu){
        EntityPlayer nmsPlayer = ((CraftPlayer) menu.getPlayer()).getHandle();
        AnvilContainer container = new AnvilContainer(nmsPlayer, menu);
        Inventory inv = container.getBukkitView().getTopInventory();

        for(int slot = 0; slot < menu.getItems().length; slot++){
            org.bukkit.inventory.ItemStack item = menu.getItems()[slot];

            if(item != null){
                inv.setItem(slot, item);
            }
        }

        int windowId = nmsPlayer.nextContainerCounter();

        nmsPlayer.playerConnection.sendPacket(new PacketPlayOutOpenWindow(windowId, "minecraft:anvil", PACKET_MESSAGE, 0));
        nmsPlayer.activeContainer = container;
        nmsPlayer.activeContainer.windowId = windowId;
        nmsPlayer.activeContainer.addSlotListener(nmsPlayer);

        return inv;
    }

}
