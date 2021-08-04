package de.amin.mechanics;

import de.amin.kit.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class RecipeLoader {

    private KitManager kitManager;
    private ShapelessRecipe grassRecipe;

    public RecipeLoader(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    public void registerRecipes() {
        ItemStack soup = new ItemStack(Material.MUSHROOM_SOUP);
        ShapelessRecipe cocoRecipe = new ShapelessRecipe(soup);
        cocoRecipe.addIngredient(Material.INK_SACK, 3);
        cocoRecipe.addIngredient(Material.BOWL);

        ShapelessRecipe cactiRecipe = new ShapelessRecipe(soup);
        cactiRecipe.addIngredient(2, Material.CACTUS);
        cactiRecipe.addIngredient(Material.BOWL);


        Bukkit.addRecipe(cactiRecipe);
        Bukkit.addRecipe(cocoRecipe);
    }


}


