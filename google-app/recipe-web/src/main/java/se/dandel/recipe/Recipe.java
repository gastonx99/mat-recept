package se.dandel.recipe;

import java.io.Serializable;

public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RecipeId id;
    private String name;
    private String type;

    public Recipe(String id) {
        this.id = new RecipeId(id);
    }

    public RecipeId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
