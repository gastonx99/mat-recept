package se.dandel.recipe.web;

import javax.faces.bean.ManagedBean;

@ManagedBean
public class RecipesForm {

    private String selectedType;

    public String getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(String selectedType) {
        this.selectedType = selectedType;
    }

}
