package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.RecipeType;
import se.dandel.recipe.service.RecipeService;

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
        List<Recipe> recipes = recipeService.findAllRecipes();
        for (Recipe recipe : recipes) {
            items.add(new RecipeItem(recipe));
        }

        typeOptions = new ArrayList<>();
        typeOptions.add(new SelectItem("", "Alla"));
        for (RecipeType type : RecipeType.values()) {
            typeOptions.add(new SelectItem(type.name()));
        }
    }

    public List<RecipeItem> getItems() {
        return items;
    }

    public List<SelectItem> getTypeOptions() {
        return typeOptions;
    }

}
