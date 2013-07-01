package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

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
    private RecipesModel model;

    @ManagedProperty(value = "#{recipesForm}")
    private RecipesForm form;

    @ManagedProperty(value = "#{editRecipeController}")
    private EditRecipeController editRecipeController;

    @Inject
    private RecipeService recipeService;

    public void load() {
        if (!FacesContext.getCurrentInstance().isPostback()) {
            logger.info("Initializing model");
            Collection<Recipe> recipes = recipeService.findAllRecipes();

            recipes.addAll(recipeService.load(getClass().getResource("/recipes.xml")));

            Set<String> types = new HashSet<>();
            Collection<RecipeItem> items = new ArrayList<>();
            for (Recipe recipe : recipes) {
                items.add(new RecipeItem(recipe));
                types.add(recipe.getType());
            }

            model.setItems(items);
            model.setTypes(types);
        }
    }

    public void editRecipe(SelectEvent event) {
        model.setSelectedRecipe((RecipeItem) event.getObject());

        editRecipeController.editRecipe(model.getSelectedRecipe());
    }

    public void setModel(RecipesModel model) {
        this.model = model;
    }

    public void setForm(RecipesForm form) {
        this.form = form;
    }

    public void typeChange(AjaxBehaviorEvent e) {
        model.setSelectedType(form.getSelectedType());
    }

    public void setEditRecipeController(EditRecipeController editRecipeController) {
        this.editRecipeController = editRecipeController;
    }

}
