package se.dandel.recipe;

import java.io.Serializable;

public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    private RecipeId id;
    private String name;
    private RecipeType type;

    public RecipeId getId() {
        return id;
    }

    public void setId(RecipeId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RecipeType getType() {
        return type;
    }

    public void setType(RecipeType type) {
        this.type = type;
    }

}
