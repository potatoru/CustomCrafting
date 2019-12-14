package me.wolfyscript.customcrafting.recipes.types.furnace;

import me.wolfyscript.customcrafting.recipes.Conditions;
import me.wolfyscript.customcrafting.recipes.RecipePriority;
import me.wolfyscript.customcrafting.recipes.types.CustomCookingRecipe;
import me.wolfyscript.utilities.api.config.ConfigAPI;
import me.wolfyscript.utilities.api.custom_items.CustomItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.RecipeChoice;

import java.util.ArrayList;
import java.util.List;

public class CustomFurnaceRecipe extends FurnaceRecipe implements CustomCookingRecipe<FurnaceConfig> {

    private boolean exactMeta, hidden;

    private RecipePriority priority;
    private List<CustomItem> result;
    private List<CustomItem> source;
    private String id;
    private FurnaceConfig config;
    private Conditions conditions;

    public CustomFurnaceRecipe(FurnaceConfig config) {
        super(new NamespacedKey(config.getNamespace(), config.getName()), config.getResult().get(0), new RecipeChoice.ExactChoice(new ArrayList<>(config.getSource())), config.getXP(), config.getCookingTime());
        this.id = config.getId();
        this.config = config;
        this.result = config.getResult();
        this.source = config.getSource();
        this.priority = config.getPriority();
        this.exactMeta = config.isExactMeta();
        this.conditions = config.getConditions();
        this.hidden = config.isHidden();
        setGroup(config.getGroup());
    }

    public List<CustomItem> getSource() {
        return source;
    }

    @Override
    public List<CustomItem> getCustomResults() {
        return result;
    }

    @Override
    public RecipePriority getPriority() {
        return priority;
    }

    @Override
    public void load() {

    }

    @Override
    public CustomFurnaceRecipe save(ConfigAPI configAPI, String namespace, String key) {
        return null;
    }

    @Override
    public CustomFurnaceRecipe save(FurnaceConfig config) {
        return null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public FurnaceConfig getConfig() {
        return config;
    }

    @Override
    public boolean isExactMeta() {
        return exactMeta;
    }

    @Override
    public Conditions getConditions() {
        return conditions;
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }
}
