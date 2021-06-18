package me.wolfyscript.customcrafting.recipes.types;

import me.wolfyscript.customcrafting.recipes.types.workbench.CraftingData;
import me.wolfyscript.customcrafting.utils.geom.Vec2d;
import me.wolfyscript.customcrafting.utils.recipe_item.Ingredient;
import me.wolfyscript.utilities.api.inventory.custom_items.CustomItem;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public interface IShapedCraftingRecipe {

    String[] getShapeMirrorHorizontal();

    String[] getShapeMirrorVertical();

    String[] getShapeRotated();

    String[] getShape();

    int getWidth();

    int getHeight();

    void setMirrorHorizontal(boolean mirrorHorizontal);

    void setMirrorVertical(boolean mirrorHorizontal);

    void setMirrorRotation(boolean mirrorHorizontal);

    boolean mirrorHorizontal();

    boolean mirrorVertical();

    boolean mirrorRotation();

    void constructShape();

    default CraftingData checkShape(CraftingRecipe<?> recipe, Map<Character, Ingredient> recipeIngredients, boolean exactMeta, ItemStack[] ingredients, List<List<ItemStack>> matrix, String[] shape) {
        if (recipeIngredients == null || recipeIngredients.isEmpty() || matrix.size() != shape.length || matrix.get(0).size() != shape[0].length()) {
            return null;
        }
        List<Character> containedKeys = new ArrayList<>();
        Map<Vec2d, CustomItem> foundItems = new HashMap<>();
        Map<Ingredient, Vec2d> mappedIngredients = new HashMap<>();
        for (int column = 0; column < matrix.size(); column++) {
            for (int row = 0; row < matrix.get(column).size(); row++) {
                ItemStack targetItem = matrix.get(column).get(row);
                char key = shape[column].charAt(row);
                if ((targetItem == null && key != ' ') || (targetItem != null && key == ' ')) return null;
                if (targetItem != null) {
                    var ingredient = recipeIngredients.get(key);
                    if (ingredient != null) {
                        Optional<CustomItem> item = ingredient.check(targetItem, exactMeta);
                        if (item.isPresent()) {
                            var vec = new Vec2d(row, column);
                            foundItems.put(vec, item.get());
                            mappedIngredients.put(ingredient, vec);
                            containedKeys.add(key);
                        }
                    }
                }
            }
        }
        return containedKeys.containsAll(recipeIngredients.keySet()) ? new CraftingData(recipe, foundItems, mappedIngredients, ingredients) : null;


    }

}
