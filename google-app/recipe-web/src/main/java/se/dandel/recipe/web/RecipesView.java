package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

@ManagedBean
public class RecipesView implements Serializable {

    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    private List<RecipeItem> items;

    private List<SelectItem> typeOptions;

    public RecipesView() {
        logger.info("Instantiating");
    }

    public List<RecipeItem> getItems() {
        if (items == null) {
            items = recipesModel.getItems();
        }
        return items;
    }

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

    public List<SelectItem> getTypeOptions() {
        if (typeOptions == null) {
            typeOptions = recipesModel.getTypeOptions();
        }
        return typeOptions;
    }

}
