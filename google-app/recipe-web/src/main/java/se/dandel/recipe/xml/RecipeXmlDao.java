package se.dandel.recipe.xml;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.util.JaxbUtil;
import se.dandel.recipe.xml.data.RecipeType;
import se.dandel.recipe.xml.data.Recipes;

public class RecipeXmlDao {

    public Collection<Recipe> load(URL url) {
        try {
            Collection<Recipe> recipes = new ArrayList<>();
            List<RecipeType> list = JaxbUtil.fromXml(IOUtils.toString(url, "utf-8"), Recipes.class).getRecipe();
            for (RecipeType r : list) {
                Recipe recipe = new Recipe(r.getKey());
                recipe.setName(r.getName());
                recipe.setType(r.getType());
                recipes.add(recipe);
            }
            return recipes;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
