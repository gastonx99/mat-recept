package se.dandel.recipe.web;

import java.io.Serializable;

import se.dandel.recipe.Recipe;

public class RecipeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private Recipe recipe;

    public RecipeItem(Recipe recipe) {
        this.recipe = recipe;
    }

    public Object getId() {
        return recipe.getId().getId();
    }

    public String getName() {
        return recipe.getName();
    }

    public String getType() {
        return recipe.getType();
    }

}
