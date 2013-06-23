package se.dandel.recipe.web;

import java.io.Serializable;

import se.dandel.recipe.Recipe;

public class RecipeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Recipe recipe;

    public Object getId() {
        return recipe.getId().getId();
    }

    public String getName() {
        return recipe.getName();
    }

    public String getType() {
        return recipe.getType().name();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
