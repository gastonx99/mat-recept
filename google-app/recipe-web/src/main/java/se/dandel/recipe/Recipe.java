package se.dandel.recipe;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import se.dandel.recipe.xml.RecipeIngredient;

public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RecipeId id;
    private String name;
    private String reference;
    private String type;
    private List<RecipeIngredient> ingredients = new ArrayList<>();
    private List<String> steps = new ArrayList<>();

    private Long preparationTime;

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

    public void addIngredient(RecipeIngredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void setPreparationTime(BigInteger time) {
        this.preparationTime = time == null ? null : time.longValue();
    }

    public void addStep(String step) {
        this.steps.add(step);
    }

    public Long getPreparationTime() {
        return preparationTime;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

}
