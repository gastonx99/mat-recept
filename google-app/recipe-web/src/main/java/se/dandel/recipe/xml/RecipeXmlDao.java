package se.dandel.recipe.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.util.JaxbUtil;
import se.dandel.recipe.xml.data.IngredientType;
import se.dandel.recipe.xml.data.IngredientsType;
import se.dandel.recipe.xml.data.PreparationType;
import se.dandel.recipe.xml.data.RecipeType;
import se.dandel.recipe.xml.data.Recipes;

public class RecipeXmlDao {

    public Collection<Recipe> load(URL url) {
        try {
            Collection<Recipe> recipes = new ArrayList<>();
            List<RecipeType> list = JaxbUtil.fromXml(IOUtils.toString(url, "utf-8"), Recipes.class).getRecipe();
            for (RecipeType r : list) {
                recipes.add(convert(r));
            }
            return recipes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Recipe convert(RecipeType r) {
        Recipe recipe = new Recipe(r.getKey());
        recipe.setName(r.getName());
        recipe.setType(r.getType());

        IngredientsType ingredients = r.getIngredients();
        if (ingredients != null) {
            for (IngredientType it : ingredients.getIngredient()) {
                RecipeIngredient i = new RecipeIngredient();
                i.setAmount(it.getAmount());
                i.setName(it.getName());
                i.setUnit(it.getUnit());
                recipe.addIngredient(i);
            }
        }

        PreparationType preparation = r.getPreparation();
        if (preparation != null) {
            recipe.setPreparationTime(preparation.getTime());
            List<String> list = preparation.getSteps().getStep();
            for (String step : list) {
                recipe.addStep(step);
            }
        }

        return recipe;
    }

}
