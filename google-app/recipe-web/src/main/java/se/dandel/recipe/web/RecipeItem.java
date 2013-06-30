package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.xml.RecipeIngredient;

public class RecipeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Recipe recipe;
    private List<RecipeIngredientItem> ingredients = new ArrayList<>();
    private List<RecipeStepItem> steps = new ArrayList<>();

    public RecipeItem(Recipe recipe) {
        this.recipe = recipe;
        for (RecipeIngredient i : recipe.getIngredients()) {
            ingredients.add(new RecipeIngredientItem(i));
        }
        for (String step : recipe.getSteps()) {
            steps.add(new RecipeStepItem(step));
        }
    }

    public String getId() {
        return recipe.getId().getId();
    }

    public String getName() {
        return recipe.getName();
    }

    public String getReference() {
        return recipe.getReference();
    }

    public String getType() {
        return Types.get(recipe.getType());
    }

    public String getPreparationTime() {
        return recipe.getPreparationTime() == null ? StringUtils.EMPTY : recipe.getPreparationTime().toString();
    }

    public List<RecipeIngredientItem> getIngredients() {
        return ingredients;
    }

    public List<RecipeStepItem> getSteps() {
        return steps;
    }

}
