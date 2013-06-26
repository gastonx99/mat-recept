package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.RecipeService;

import com.google.inject.Inject;

@ManagedBean
public class RecipesController implements Serializable {
    private static final Logger logger = Logger.getLogger(RecipesController.class.getName());
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    @ManagedProperty(value = "#{recipesForm}")
    private RecipesForm recipesForm;

    @Inject
    private RecipeService recipeService;

    public void load() {
        logger.info("Initializing model");
        Collection<Recipe> recipes = recipeService.findAllRecipes();

        recipes.addAll(recipeService.load(getClass().getResource("/recipes.xml")));

        Set<String> types = new HashSet<>();
        Collection<RecipeItem> items = new ArrayList<>();
        for (Recipe recipe : recipes) {
            items.add(new RecipeItem(recipe));
            types.add(Types.get(recipe.getType()));
        }

        recipesModel.setItems(items);
        recipesModel.setTypes(types);

    }

    public void editRecipe(SelectEvent event) {
        recipesModel.setSelectedRecipe((RecipeItem) event.getObject());

        FacesContext context = FacesContext.getCurrentInstance();
        ConfigurableNavigationHandler navigationHandler = (ConfigurableNavigationHandler) context.getApplication()
                .getNavigationHandler();
        navigationHandler.performNavigation("edit-recipe?faces-redirect=true");
    }

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

    public void setRecipesForm(RecipesForm recipesForm) {
        this.recipesForm = recipesForm;
    }

    public void typeChange(AjaxBehaviorEvent e) {
        logger.info("typeChange: " + recipesForm.getSelectedType());
    }

}
