package me.wolfyscript.customcrafting.gui.recipe_creator.recipe_creators;

import me.wolfyscript.customcrafting.CustomCrafting;
import me.wolfyscript.customcrafting.data.CCCache;
import me.wolfyscript.customcrafting.gui.RecipeCreatorCluster;
import me.wolfyscript.customcrafting.gui.recipe_creator.buttons.ButtonRecipeIngredient;
import me.wolfyscript.customcrafting.gui.recipe_creator.buttons.ButtonRecipeResult;
import me.wolfyscript.utilities.api.inventory.gui.GuiCluster;
import me.wolfyscript.utilities.api.inventory.gui.GuiUpdate;
import me.wolfyscript.utilities.api.inventory.gui.button.ButtonState;
import me.wolfyscript.utilities.api.inventory.gui.button.buttons.ToggleButton;
import me.wolfyscript.utilities.util.inventory.PlayerHeadUtils;

public class CraftingCreator extends RecipeCreator {

    public static final String KEY = "crafting";

    public CraftingCreator(GuiCluster<CCCache> cluster, CustomCrafting customCrafting) {
        super(cluster, KEY, 45, customCrafting);
    }

    @Override
    public void onInit() {
        super.onInit();

        for (int i = 0; i < 9; i++) {
            registerButton(new ButtonRecipeIngredient(i));
        }

        registerButton(new ButtonRecipeResult());

        registerButton(new ToggleButton<>(RecipeCreatorCluster.SHAPELESS, (cache, guiHandler, player, guiInventory, i) -> cache.getRecipeCreatorCache().getCraftingCache().isShapeless(), new ButtonState<>(RecipeCreatorCluster.SHAPELESS_ENABLED, PlayerHeadUtils.getViaURL("f21d93da43863cb3759afefa9f7cc5c81f34d920ca97b7283b462f8b197f813"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setShapeless(false);
            return true;
        }), new ButtonState<>(RecipeCreatorCluster.SHAPELESS_DISABLED, PlayerHeadUtils.getViaURL("1aae7e8222ddbee19d184b97e79067814b6ba3142a3bdcce8b93099a312"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setShapeless(true);
            return true;
        })));

        registerButton(new ToggleButton<>(RecipeCreatorCluster.MIRROR_HORIZONTAL, (cache, guiHandler, player, guiInventory, i) -> cache.getRecipeCreatorCache().getCraftingCache().isMirrorHorizontal(), new ButtonState<>(RecipeCreatorCluster.MIRROR_HORIZONTAL_ENABLED, PlayerHeadUtils.getViaURL("956a3618459e43b287b22b7e235ec699594546c6fcd6dc84bfca4cf30ab9311"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setMirrorHorizontal(false);
            return true;
        }), new ButtonState<>(RecipeCreatorCluster.MIRROR_HORIZONTAL_DISABLED, PlayerHeadUtils.getViaURL("956a3618459e43b287b22b7e235ec699594546c6fcd6dc84bfca4cf30ab9311"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setMirrorHorizontal(true);
            return true;
        })));
        registerButton(new ToggleButton<>(RecipeCreatorCluster.MIRROR_VERTICAL, (cache, guiHandler, player, guiInventory, i) -> cache.getRecipeCreatorCache().getCraftingCache().isMirrorVertical(), new ButtonState<>(RecipeCreatorCluster.MIRROR_VERTICAL_ENABLED, PlayerHeadUtils.getViaURL("882faf9a584c4d676d730b23f8942bb997fa3dad46d4f65e288c39eb471ce7"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setMirrorVertical(false);
            return true;
        }), new ButtonState<>(RecipeCreatorCluster.MIRROR_VERTICAL_DISABLED, PlayerHeadUtils.getViaURL("882faf9a584c4d676d730b23f8942bb997fa3dad46d4f65e288c39eb471ce7"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setMirrorVertical(true);
            return true;
        })));
        registerButton(new ToggleButton<>(RecipeCreatorCluster.MIRROR_ROTATION, (cache, guiHandler, player, guiInventory, i) -> cache.getRecipeCreatorCache().getCraftingCache().isMirrorRotation(), new ButtonState<>(RecipeCreatorCluster.MIRROR_ROTATION_ENABLED, PlayerHeadUtils.getViaURL("e887cc388c8dcfcf1ba8aa5c3c102dce9cf7b1b63e786b34d4f1c3796d3e9d61"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setMirrorRotation(false);
            return true;
        }), new ButtonState<>(RecipeCreatorCluster.MIRROR_ROTATION_DISABLED, PlayerHeadUtils.getViaURL("e887cc388c8dcfcf1ba8aa5c3c102dce9cf7b1b63e786b34d4f1c3796d3e9d61"), (cache, guiHandler, player, inventory, slot, event) -> {
            cache.getRecipeCreatorCache().getCraftingCache().setMirrorRotation(true);
            return true;
        })));
    }

    @Override
    public void onUpdateAsync(GuiUpdate<CCCache> update) {
        super.onUpdateAsync(update);
        update.setButton(0, BACK);
        CCCache cache = update.getGuiHandler().getCustomCache();
        var craftingRecipe = cache.getRecipeCreatorCache().getCraftingCache();

        if (!craftingRecipe.isShapeless()) {
            if (craftingRecipe.isMirrorHorizontal() && craftingRecipe.isMirrorVertical()) {
                update.setButton(37, RecipeCreatorCluster.MIRROR_ROTATION);
            }
            update.setButton(38, RecipeCreatorCluster.MIRROR_HORIZONTAL);
            update.setButton(39, RecipeCreatorCluster.MIRROR_VERTICAL);
        }

        for (int i = 0; i < 9; i++) {
            update.setButton(10 + i + (i / 3) * 6, "recipe.ingredient_" + i);
        }
        update.setButton(22, RecipeCreatorCluster.SHAPELESS);
        update.setButton(24, "recipe.result");

        update.setButton(1, RecipeCreatorCluster.HIDDEN);
        update.setButton(3, RecipeCreatorCluster.CONDITIONS);
        update.setButton(5, RecipeCreatorCluster.EXACT_META);
        update.setButton(7, RecipeCreatorCluster.PRIORITY);

        update.setButton(42, RecipeCreatorCluster.GROUP);
        if (craftingRecipe.isSaved()) {
            update.setButton(43, RecipeCreatorCluster.SAVE);
        }
        update.setButton(44, RecipeCreatorCluster.SAVE_AS);
    }

}
