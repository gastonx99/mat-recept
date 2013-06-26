package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class EditRecipeView implements Serializable {

    private static final Logger logger = Logger.getLogger(EditRecipeView.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    public EditRecipeView() {
        logger.info("Instantiating");
    }

    public RecipeItem getRecipe() {
        return recipesModel.getSelectedRecipe();
    }

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

}
