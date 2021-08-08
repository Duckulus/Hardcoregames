//Created by Duckulus on 05 Jul, 2021 

package de.amin.mechanics.conversation;

import de.amin.inventories.KitSearchInventory;
import de.amin.hardcoregames.HG;
import de.amin.kit.KitManager;
import fr.minuskube.inv.SmartInventory;
import org.bukkit.ChatColor;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class KitSearchPromt extends StringPrompt {

    private KitManager kitManager;

    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return "§9Please enter your search query: §7(\"exit\" to abort)";
    }

    @Override
    public Prompt acceptInput(ConversationContext c, String s) {
        Conversable cpo = c.getForWhom();

        final SmartInventory INVENTORY = SmartInventory.builder()
                .id("myInventory")
                .provider(new KitSearchInventory(s))
                .size(6, 9)
                .title(ChatColor.BLUE + "Kit Selector")
                .manager(HG.INSTANCE.getInventoryManager())
                .build();
        INVENTORY.open((Player) cpo);

        return END_OF_CONVERSATION;
    }
}
