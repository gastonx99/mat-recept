package se.dandel.recipe;

import java.io.Serializable;

public class RecipeId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    public RecipeId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}
