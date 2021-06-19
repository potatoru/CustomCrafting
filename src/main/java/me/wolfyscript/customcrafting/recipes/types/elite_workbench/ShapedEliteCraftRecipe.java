package me.wolfyscript.customcrafting.recipes.types.elite_workbench;

import me.wolfyscript.customcrafting.recipes.RecipePacketType;
import me.wolfyscript.customcrafting.recipes.types.IShapedCraftingRecipe;
import me.wolfyscript.customcrafting.recipes.types.workbench.CraftingData;
import me.wolfyscript.customcrafting.utils.recipe_item.Ingredient;
import me.wolfyscript.utilities.libraries.com.fasterxml.jackson.core.JsonGenerator;
import me.wolfyscript.utilities.libraries.com.fasterxml.jackson.databind.JsonNode;
import me.wolfyscript.utilities.libraries.com.fasterxml.jackson.databind.SerializerProvider;
import me.wolfyscript.utilities.util.NamespacedKey;
import me.wolfyscript.utilities.util.RecipeUtil;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ShapedEliteCraftRecipe extends EliteCraftingRecipe implements IShapedCraftingRecipe {

    private int width;
    private int height;
    private String[] shape, shapeMirrorHorizontal, shapeMirrorVertical, shapeRotated;
    private boolean mirrorHorizontal, mirrorVertical, mirrorRotation;

    public ShapedEliteCraftRecipe(NamespacedKey namespacedKey, JsonNode node) {
        super(namespacedKey, node);
        this.shapeless = false;
        constructShape();
        JsonNode mirrorNode = node.path("mirror");
        this.mirrorHorizontal = mirrorNode.path("horizontal").asBoolean(false);
        this.mirrorVertical = mirrorNode.path("vertical").asBoolean(false);
        this.mirrorRotation = mirrorNode.path("rotation").asBoolean(false);
    }

    public ShapedEliteCraftRecipe() {
        super();
        this.shapeless = false;
        this.mirrorHorizontal = true;
        this.mirrorVertical = false;
        this.mirrorRotation = false;
    }

    public ShapedEliteCraftRecipe(ShapedEliteCraftRecipe eliteCraftingRecipe) {
        super(eliteCraftingRecipe);
        this.shapeless = false;
        constructShape();
        this.mirrorHorizontal = eliteCraftingRecipe.mirrorHorizontal;
        this.mirrorVertical = eliteCraftingRecipe.mirrorVertical;
        this.mirrorRotation = eliteCraftingRecipe.mirrorRotation;
    }

    public ShapedEliteCraftRecipe(EliteCraftingRecipe eliteCraftingRecipe) {
        super(eliteCraftingRecipe);
        this.shapeless = false;
        constructShape();
        this.mirrorHorizontal = true;
        this.mirrorVertical = false;
        this.mirrorRotation = false;
    }

    @Override
    public String[] getShapeMirrorHorizontal() {
        return shapeMirrorHorizontal;
    }

    @Override
    public String[] getShapeMirrorVertical() {
        return shapeMirrorVertical;
    }

    @Override
    public String[] getShapeRotated() {
        return shapeRotated;
    }

    @Override
    public String[] getShape() {
        return shape;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public boolean mirrorHorizontal() {
        return mirrorHorizontal;
    }

    @Override
    public boolean mirrorVertical() {
        return mirrorVertical;
    }

    @Override
    public boolean mirrorRotation() {
        return mirrorRotation;
    }

    @Override
    public void constructShape() {
        Map<Character, Ingredient> ingredients = getIngredients();
        String[] shape = new String[6];
        int index = 0;
        int row = 0;
        for (int i = 0; i < 36; i++) {
            char ingrd = LETTERS.charAt(i);
            Ingredient items = ingredients.get(ingrd);
            if (items == null || items.isEmpty()) {
                if (shape[row] != null) {
                    shape[row] = shape[row] + " ";
                } else {
                    shape[row] = " ";
                }
            } else {
                if (shape[row] != null) {
                    shape[row] = shape[row] + ingrd;
                } else {
                    shape[row] = String.valueOf(ingrd);
                }
            }
            index++;
            if ((index % 6) == 0) {
                row++;
            }
        }
        this.shape = RecipeUtil.formatShape(shape).toArray(new String[0]);
        this.shapeMirrorVertical = new String[]{"      ", "      ", "      ", "      ", "      ", "      "};
        int j = 0;
        for (int i = shape.length - 1; i > 0; i--) {
            this.shapeMirrorVertical[j] = shape[i];
            j++;
        }
        this.shapeMirrorVertical = RecipeUtil.formatShape(this.shapeMirrorVertical).toArray(new String[0]);
        this.shapeMirrorHorizontal = this.shape.clone();
        for (int i = 0; i < this.shapeMirrorHorizontal.length; i++) {
            this.shapeMirrorHorizontal[i] = new StringBuilder(this.shapeMirrorHorizontal[i]).reverse().toString();
        }
        this.shapeRotated = this.shapeMirrorVertical.clone();
        for (int i = 0; i < this.shapeRotated.length; i++) {
            this.shapeRotated[i] = new StringBuilder(this.shapeRotated[i]).reverse().toString();
        }
        this.width = this.shape.length > 0 ? this.shape[0].length() : 0;
        this.height = this.shape.length;
    }

    public void setMirrorHorizontal(boolean mirrorHorizontal) {
        this.mirrorHorizontal = mirrorHorizontal;
    }

    public void setMirrorVertical(boolean mirrorVertical) {
        this.mirrorVertical = mirrorVertical;
    }

    public void setMirrorRotation(boolean mirrorRotation) {
        this.mirrorRotation = mirrorRotation;
    }

    @Override
    public boolean isShapeless() {
        return false;
    }

    @Override
    public CraftingData check(List<List<ItemStack>> ingredients) {
        CraftingData craftingData = checkShape(ingredients, getShape());
        if (craftingData != null) {
            return craftingData;
        }
        if (mirrorHorizontal()) {
            craftingData = checkShape(ingredients, getShapeMirrorHorizontal());
            if (craftingData != null) {
                return craftingData;
            }
        }
        if (mirrorVertical()) {
            craftingData = checkShape(ingredients, getShapeMirrorVertical());
            if (craftingData != null) {
                return craftingData;
            }
        }
        if (mirrorHorizontal() && mirrorVertical() && mirrorRotation()) {
            craftingData = checkShape(ingredients, getShapeRotated());
            return craftingData;
        }
        return null;
    }

    private CraftingData checkShape(List<List<ItemStack>> matrix, String[] shape) {
        return checkShape(this, getIngredients(), isExactMeta(), matrix, shape);
    }

    @Override
    public RecipePacketType getPacketType() {
        return RecipePacketType.ELITE_CRAFTING_SHAPED;
    }

    @Override
    public ShapedEliteCraftRecipe clone() {
        return new ShapedEliteCraftRecipe(this);
    }

    @Override
    public void writeToJson(JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        super.writeToJson(gen, serializerProvider);

        gen.writeObjectField("shape", shape);
        gen.writeObjectFieldStart("mirror");
        gen.writeBooleanField("horizontal", this.mirrorHorizontal);
        gen.writeBooleanField("vertical", this.mirrorVertical);
        gen.writeBooleanField("rotation", this.mirrorRotation);
        gen.writeEndObject();
    }
}