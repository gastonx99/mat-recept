package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RecipesModel implements Serializable {
    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    private List<RecipeItem> items;

    public List<RecipeItem> getItems() {
        logger.info("getItems");
        return items;
    }

    void setItems(List<RecipeItem> items) {
        this.items = items;
    }

}
