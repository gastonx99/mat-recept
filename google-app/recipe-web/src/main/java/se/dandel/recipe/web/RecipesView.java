package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.builder.CompareToBuilder;

@ManagedBean
public class RecipesView implements Serializable {

    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final Comparator<RecipeItem> COMPARATOR_TYPE_NAME = new Comparator<RecipeItem>() {
        @Override
        public int compare(RecipeItem o1, RecipeItem o2) {
            CompareToBuilder builder = new CompareToBuilder();
            builder.append(o1.getType(), o2.getType());
            builder.append(o1.getName(), o2.getName());
            return builder.toComparison();
        }
    };

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    public RecipesView() {
        logger.info("Instantiating");
    }

    public List<RecipeItem> getItems() {
        List<RecipeItem> items = new ArrayList<>(recipesModel.getItems());
        Collections.sort(items, COMPARATOR_TYPE_NAME);
        return items;
    }

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

    public List<SelectItem> getTypeOptions() {
        List<String> sortedTypes = new ArrayList<>(recipesModel.getTypes());
        Collections.sort(sortedTypes);

        List<SelectItem> typeOptions = new ArrayList<>();
        typeOptions.add(new SelectItem("", "Alla"));
        for (String type : sortedTypes) {
            typeOptions.add(new SelectItem(type));
        }
        return typeOptions;
    }

}
