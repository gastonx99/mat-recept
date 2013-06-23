package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import se.dandel.recipe.Recipe;
import se.dandel.recipe.RecipeId;
import se.dandel.recipe.RecipeType;

@ManagedBean
public class RecipesController implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    public void load() {
        System.out.println("qwerqwer");
        List<RecipeItem> items = new ArrayList<>();
        items.add(createItem(RecipeType.MEAT, "BIFF_STROGANOFF", "Biff Stroganoff"));
        items.add(createItem(RecipeType.ITRIM_LCD, "ITRIM_LCD_CHOCOLATE", "Itrim LCD Choklad"));
        items.add(createItem(RecipeType.FISH, "FISK_GRYTA_SAFFRAN_RAKOR", "Lyxig fiskgryta med saffran och räkor"));
        recipesModel.setItems(items);
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

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

}
