package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.Collection;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RecipesModel implements Serializable {
    @SuppressWarnings("unused")
    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    private Collection<RecipeItem> items;

    private Collection<String> types;

    private RecipeItem selectedRecipe;

    public Collection<RecipeItem> getItems() {
        return items;
    }

    public Collection<String> getTypes() {
        return types;
    }

    void setItems(Collection<RecipeItem> items) {
        this.items = items;
    }

    void setTypes(Collection<String> types) {
        this.types = types;
    }

    public void setSelectedRecipe(RecipeItem selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    public RecipeItem getSelectedRecipe() {
        return selectedRecipe;
    }

}
