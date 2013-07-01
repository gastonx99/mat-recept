package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

@ManagedBean
@ViewScoped
public class RecipesModel implements Serializable {
    private static final Comparator<RecipeItem> RECIPE_COMPARATOR_TYPE_NAME = new Comparator<RecipeItem>() {
        @Override
        public int compare(RecipeItem o1, RecipeItem o2) {
            CompareToBuilder builder = new CompareToBuilder();
            builder.append(o1.getType(), o2.getType());
            builder.append(o1.getName(), o2.getName());
            return builder.toComparison();
        }
    };

    private static final Logger logger = Logger.getLogger(RecipesView.class.getName());

    private static final long serialVersionUID = 1L;

    private Collection<RecipeItem> items;

    private Collection<String> types;

    private RecipeItem selectedRecipe;

    private String selectedType;

    public List<RecipeItem> getItems() {
        List<RecipeItem> list = new ArrayList<>();
        if (StringUtils.isEmpty(selectedType)) {
            list.addAll(items);
        } else {
            logger.fine("Filtering on " + selectedType);
            for (RecipeItem i : items) {
                if (i.getRecipe().getType().equals(selectedType)) {
                    list.add(i);
                }
            }
        }
        Collections.sort(list, RECIPE_COMPARATOR_TYPE_NAME);
        return list;
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

    void setSelectedRecipe(RecipeItem selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
    }

    public RecipeItem getSelectedRecipe() {
        return selectedRecipe;
    }

    void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

    public String getSelectedType() {
        return selectedType;
    }

}
