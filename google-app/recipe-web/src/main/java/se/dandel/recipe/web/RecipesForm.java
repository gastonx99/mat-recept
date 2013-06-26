package se.dandel.recipe.web;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RecipesForm {

    private String selectedType;

    private RecipeItem selectedRecipe;

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public RecipeItem getSelectedRecipe() {
        return selectedRecipe;
    }

    public void setSelectedRecipe(RecipeItem selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

}
