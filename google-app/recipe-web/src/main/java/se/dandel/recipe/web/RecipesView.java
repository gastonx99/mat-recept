package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

@ManagedBean
public class RecipesView implements Serializable {

    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    private static final Comparator<SelectItem> TYPE_COMPARATOR = new Comparator<SelectItem>() {

        @Override
        public int compare(SelectItem o1, SelectItem o2) {
            return o1.getLabel().compareTo(o2.getLabel());
        }
    };

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    private List<RecipeItem> items;

    private List<SelectItem> typeOptions;

    public RecipesView() {
        logger.info("Instantiating");
    }

    public List<RecipeItem> getItems() {
        if (items == null) {
            items = new ArrayList<>(recipesModel.getItems());
        }
        return items;
    }

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

    public List<SelectItem> getTypeOptions() {
        if (typeOptions == null) {
            Collection<String> types = recipesModel.getTypes();
            typeOptions = new ArrayList<>(types.size() + 1);
            for (String type : types) {
                typeOptions.add(new SelectItem(type, Types.get(type)));
            }
            Collections.sort(typeOptions, TYPE_COMPARATOR);
            typeOptions.add(0, new SelectItem("", "Alla"));
        }
        return typeOptions;
    }

}
