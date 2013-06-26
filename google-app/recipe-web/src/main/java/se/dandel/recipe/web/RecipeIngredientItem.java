package se.dandel.recipe.web;

import java.io.Serializable;

import se.dandel.recipe.xml.RecipeIngredient;

public class RecipeIngredientItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private RecipeIngredient ingredient;

    public RecipeIngredientItem(RecipeIngredient ingredient) {
        this.ingredient = ingredient;
    }

    public String getName() {
        return ingredient.getName();
    }

    public String getAmount() {
        return ingredient.getAmount();
    }

    public String getUnit() {
        return ingredient.getUnit();
    }

}
