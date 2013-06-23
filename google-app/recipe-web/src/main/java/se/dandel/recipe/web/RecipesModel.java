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
import se.dandel.recipe.RecipeId;
import se.dandel.recipe.RecipeType;

@ManagedBean
@ViewScoped
public class RecipesModel implements Serializable {
    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    private List<RecipeItem> items;

    private List<SelectItem> typeOptions;

    @PostConstruct
    public void init() {
        logger.info("Initializing model");
        items = new ArrayList<>();
        items.add(createItem(RecipeType.MEAT, "BIFF_STROGANOFF", "Biff Stroganoff"));
        items.add(createItem(RecipeType.ITRIM_LCD, "ITRIM_LCD_CHOCOLATE", "Itrim LCD Choklad"));
        items.add(createItem(RecipeType.FISH, "FISK_GRYTA_SAFFRAN_RAKOR", "Lyxig fiskgryta med saffran och räkor"));

        typeOptions = new ArrayList<>();
        typeOptions.add(new SelectItem("", "Alla"));
        for (RecipeType type : RecipeType.values()) {
            typeOptions.add(new SelectItem(type.name()));
        }
    }

    private RecipeItem createItem(RecipeType type, String id, String name) {
        RecipeItem item = new RecipeItem();
        Recipe recipe = new Recipe();
        recipe.setType(type);
        recipe.setId(new RecipeId(id));
        recipe.setName(name);
        item.setRecipe(recipe);
        return item;
    }

    public List<RecipeItem> getItems() {
        return items;
    }

    public List<SelectItem> getTypeOptions() {
        return typeOptions;
    }

}
