package me.wolfyscript.customcrafting.configs.custom_data;

import me.wolfyscript.customcrafting.CustomCrafting;
import me.wolfyscript.utilities.api.inventory.custom_items.CustomData;
import me.wolfyscript.utilities.api.inventory.custom_items.CustomItem;
import me.wolfyscript.utilities.libraries.com.fasterxml.jackson.core.JsonGenerator;
import me.wolfyscript.utilities.libraries.com.fasterxml.jackson.databind.DeserializationContext;
import me.wolfyscript.utilities.libraries.com.fasterxml.jackson.databind.JsonNode;
import me.wolfyscript.utilities.libraries.com.fasterxml.jackson.databind.SerializerProvider;
import me.wolfyscript.utilities.util.NamespacedKey;

import java.io.IOException;
import java.util.Objects;

public class EliteWorkbenchData extends CustomData implements Cloneable {

    private boolean advancedRecipes;
    private boolean enabled;
    private boolean allowHoppers;
    private boolean keepItems;
    private int gridSize;

    protected EliteWorkbenchData(NamespacedKey namespacedKey) {
        super(namespacedKey);
        this.enabled = false;
        this.gridSize = 3;
        this.allowHoppers = false;
        this.keepItems = false;
    }

    protected EliteWorkbenchData(EliteWorkbenchData eliteWorkbenchData) {
        super(eliteWorkbenchData);
        this.enabled = eliteWorkbenchData.enabled;
        this.gridSize = eliteWorkbenchData.gridSize;
        this.allowHoppers = eliteWorkbenchData.allowHoppers;
        this.keepItems = eliteWorkbenchData.keepItems;
        this.advancedRecipes = eliteWorkbenchData.advancedRecipes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAdvancedRecipes() {
        return advancedRecipes;
    }

    public void setAdvancedRecipes(boolean advancedRecipes) {
        this.advancedRecipes = advancedRecipes;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public void setAllowHoppers(boolean allowHoppers) {
        this.allowHoppers = allowHoppers;
    }

    public boolean isAllowHoppers() {
        return allowHoppers;
    }

    public void setKeepItems(boolean keepItems) {
        this.keepItems = keepItems;
    }

    public boolean isKeepItems() {
        return keepItems;
    }

    @Override
    public void writeToJson(CustomItem customItem, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeBooleanField("enabled", enabled);
        gen.writeNumberField("gridSize", gridSize);
        gen.writeBooleanField("advancedRecipes", advancedRecipes);
        gen.writeBooleanField("allowHoppers", allowHoppers);
        gen.writeBooleanField("keepItems", keepItems);
    }

    @Override
    protected void readFromJson(CustomItem customItem, JsonNode node, DeserializationContext deserializationContext) throws IOException {
        setEnabled(node.get("enabled").asBoolean(false));
        setGridSize(node.get("gridSize").asInt(3));
        setAdvancedRecipes(node.get("advancedRecipes").asBoolean(false));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EliteWorkbenchData that)) return false;
        if (!super.equals(o)) return false;
        return advancedRecipes == that.advancedRecipes &&
                enabled == that.enabled &&
                allowHoppers == that.allowHoppers &&
                keepItems == that.keepItems &&
                gridSize == that.gridSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), advancedRecipes, enabled, allowHoppers, keepItems, gridSize);
    }

    @Override
    public String toString() {
        return "EliteWorkbenchData{" +
                "advancedRecipes=" + advancedRecipes +
                ", enabled=" + enabled +
                ", allowHoppers=" + allowHoppers +
                ", keepItems=" + keepItems +
                ", gridSize=" + gridSize +
                "} " + super.toString();
    }

    @Override
    public EliteWorkbenchData clone() {
        return new EliteWorkbenchData(this);
    }

    public static class Provider extends CustomData.Provider<EliteWorkbenchData> {

        public Provider() {
            super(CustomCrafting.ELITE_CRAFTING_TABLE, EliteWorkbenchData.class);
        }

    }
}
