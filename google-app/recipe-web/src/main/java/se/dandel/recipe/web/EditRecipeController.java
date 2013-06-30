package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.primefaces.event.RowEditEvent;

import se.dandel.recipe.RecipeService;
import se.dandel.recipe.web.EditRecipeForm.Ingredient;
import se.dandel.recipe.web.EditRecipeForm.Step;

import com.google.inject.Inject;

@ManagedBean
public class EditRecipeController implements Serializable {
    private static final Logger logger = Logger.getLogger(EditRecipeController.class.getName());
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    @ManagedProperty(value = "#{editRecipeForm}")
    private EditRecipeForm form;

    @Inject
    private RecipeService recipeService;

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

    public void setForm(EditRecipeForm form) {
        this.form = form;
    }

    public void newIngredient() {
        form.addNewEmptyIngredient();
    }

    public void newStep() {
        form.addNewEmptyStep();
    }

    public void editRecipe(RecipeItem selectedRecipe) {
        form.reset();

        RecipeItem r = recipesModel.getSelectedRecipe();
        form.setName(r.getName());
        form.setId(r.getId());
        form.setReference(r.getReference());
        form.setType(r.getType());

        for (RecipeIngredientItem item : r.getIngredients()) {
            Ingredient i = new Ingredient();
            i.setName(item.getName());
            i.setAmount(item.getAmount());
            i.setUnit(item.getUnit());
            form.addIngredient(i);
        }

        for (RecipeStepItem item : r.getSteps()) {
            Step i = new Step();
            i.setName(item.getStep());
            form.addStep(i);
        }
    }

    public void onIngredientEdit(RowEditEvent event) {
        Ingredient i = (Ingredient) event.getObject();
        i.setName(i.getName() + " foo");
        logger.info("Ingredient name " + i.getName());
    }

    public void deleteIngredient(int rowIndex) {
        form.deleteIngredient(rowIndex);
    }

    public void deleteStep(int rowIndex) {
        form.deleteStep(rowIndex);
    }
}
