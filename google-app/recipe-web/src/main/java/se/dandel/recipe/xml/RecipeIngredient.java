package se.dandel.recipe.xml;

import java.io.Serializable;

public class RecipeIngredient implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String amount;
    private String unit;

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
