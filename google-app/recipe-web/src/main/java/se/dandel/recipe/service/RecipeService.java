package se.dandel.recipe.service;

import java.util.ArrayList;
import java.util.List;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.RecipeId;
import se.dandel.recipe.RecipeType;

public class RecipeService {

    public List<Recipe> findAllRecipes() {
        List<Recipe> recipes = new ArrayList<Recipe>();
        recipes.add(createItem(RecipeType.MEAT, "BIFF_STROGANOFF", "Biff Stroganoff"));
        recipes.add(createItem(RecipeType.ITRIM_LCD, "ITRIM_LCD_CHOCOLATE", "Itrim LCD Choklad"));
        recipes.add(createItem(RecipeType.FISH, "FISK_GRYTA_SAFFRAN_RAKOR", "Lyxig fiskgryta med saffran och räkor"));
        return recipes;
    }

    private Recipe createItem(RecipeType type, String id, String name) {
        Recipe recipe = new Recipe();
        recipe.setType(type);
        recipe.setId(new RecipeId(id));
        recipe.setName(name);
        return recipe;
    }

}
