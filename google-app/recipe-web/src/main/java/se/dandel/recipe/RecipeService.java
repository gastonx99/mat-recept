package se.dandel.recipe;

import java.net.URL;
import java.util.Collection;

import se.dandel.recipe.xml.RecipeXmlDao;

import com.google.inject.Inject;

public class RecipeService {

    @Inject
    private RecipeXmlDao recipeXmlDao;

    @Inject
    private RecipeDao recipeDao;

    public Collection<Recipe> findAllRecipes() {
        return recipeDao.findAllRecipes();
    }

    public Collection<Recipe> load(URL url) {
        return recipeXmlDao.load(url);
    }

}
