package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

@ManagedBean
public class RecipesView implements Serializable {

    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    private List<RecipeItem> items;

    public RecipesView() {
        logger.info("Instantiating");
    }

    public List<RecipeItem> getItems() {
        logger.info("getItems");
        if (items == null) {
            items = recipesModel.getItems();
        }
        return items;
    }

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

}
