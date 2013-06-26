package se.dandel.recipe.web;

import java.io.Serializable;

public class RecipeStepItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String step;

    public RecipeStepItem(String step) {
        this.step = step;
    }

    public String getStep() {
        return step;
    }

}
