package me.wolfyscript.customcrafting.gui.recipebook.buttons;

import me.wolfyscript.customcrafting.CustomCrafting;
import me.wolfyscript.customcrafting.configs.recipebook.Categories;
import me.wolfyscript.customcrafting.configs.recipebook.Category;
import me.wolfyscript.customcrafting.data.CCCache;
import me.wolfyscript.customcrafting.data.cache.KnowledgeBook;
import me.wolfyscript.utilities.api.WolfyUtilities;
import me.wolfyscript.utilities.api.inventory.gui.GuiHandler;
import me.wolfyscript.utilities.api.inventory.gui.GuiWindow;
import me.wolfyscript.utilities.api.inventory.gui.button.Button;
import me.wolfyscript.utilities.api.inventory.gui.button.ButtonType;
import me.wolfyscript.utilities.api.nms.inventory.GUIInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class MainCategoryButton extends Button<CCCache> {

    private final CustomCrafting customCrafting;
    private final Categories categories;

    private final Category category;

    public MainCategoryButton(String categoryId, CustomCrafting customCrafting) {
        super("main_category." + categoryId, ButtonType.NORMAL);
        this.customCrafting = customCrafting;
        this.categories = customCrafting.getDataHandler().getCategories();
        this.category = categories.getCategory(categoryId);
    }

    @Override
    public void render(GuiHandler<CCCache> guiHandler, Player player, GUIInventory<CCCache> guiInventory, Inventory inventory, ItemStack itemStack, int slot, boolean help) {
        if (category != null) {
            inventory.setItem(slot, category.createItemStack(customCrafting));
        }
    }

    @Override
    public void init(GuiWindow<CCCache> guiWindow) {

    }

    @Override
    public void init(String s, WolfyUtilities wolfyUtilities) {

    }

    @Override
    public void postExecute(GuiHandler<CCCache> guiHandler, Player player, GUIInventory<CCCache> inventory, ItemStack itemStack, int slot, InventoryInteractEvent event) throws IOException {

    }

    @Override
    public void preRender(GuiHandler<CCCache> guiHandler, Player player, GUIInventory<CCCache> inventory, ItemStack itemStack, int slot, boolean help) {

    }

    @Override
    public boolean execute(GuiHandler<CCCache> guiHandler, Player player, GUIInventory<CCCache> inventory, int slot, InventoryInteractEvent event) {
        if (category != null) {
            KnowledgeBook knowledgeBook = guiHandler.getCustomCache().getKnowledgeBook();
            knowledgeBook.setCategory(category);
            guiHandler.openWindow("recipe_book");
        }
        return true;
    }
}
