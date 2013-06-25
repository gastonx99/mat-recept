package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.RecipeService;

import com.google.inject.Inject;

@ManagedBean
@ViewScoped
public class RecipesModel implements Serializable {
    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    private List<RecipeItem> items = new ArrayList<>();

    private List<SelectItem> typeOptions;

    @Inject
    private RecipeService recipeService;

    @PostConstruct
    public void init() {
        logger.info("Initializing model");
        Collection<Recipe> recipes = recipeService.findAllRecipes();

        recipes.addAll(recipeService.load(getClass().getResource("/recipes.xml")));

        Set<String> types = new HashSet<>();
        for (Recipe recipe : recipes) {
            items.add(new RecipeItem(recipe));
            types.add(recipe.getType());
        }

        typeOptions = new ArrayList<>();
        typeOptions.add(new SelectItem("", "Alla"));
        for (String type : types) {
            typeOptions.add(new SelectItem(type));
        }
    }

    public List<RecipeItem> getItems() {
        return items;
    }

    public List<SelectItem> getTypeOptions() {
        return typeOptions;
    }

}
