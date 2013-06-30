package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang.StringUtils;

@ManagedBean
@ViewScoped
public class EditRecipeForm implements Serializable {
    private static final Logger logger = Logger.getLogger(EditRecipeController.class.getName());

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String reference;

    private String type;

    private List<Ingredient> ingredients = new ArrayList<>();

    private List<Step> steps = new ArrayList<>();

    public static class Step {
        private String name;
        private boolean newStep;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            newStep = false;
            this.name = name;
        }

        public boolean isNewStep() {
            return newStep;
        }

        public boolean isEmpty() {
            return StringUtils.isEmpty(name);
        }

    }

    public static class Ingredient {
        private String name;
        private String amount;
        private String unit;

        private boolean newIngredient;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            newIngredient = false;
            this.name = name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            newIngredient = false;
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            newIngredient = false;
            this.unit = unit;
        }

        public boolean isNew() {
            return newIngredient;
        }

        public boolean isEmpty() {
            return StringUtils.isEmpty(name) && StringUtils.isEmpty(amount) && StringUtils.isEmpty(unit);
        }

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void addNewEmptyIngredient() {
        logger.info("Adding new empty ingredient");
        Ingredient i = new Ingredient();
        i.setName("<new>");
        ingredients.add(i);
        i.newIngredient = true;
        logger.info("Number of ingredients are now " + ingredients.size());
    }

    public void addNewEmptyStep() {
        logger.info("Adding new empty step");
        Step i = new Step();
        i.setName("<step>");
        steps.add(i);
        i.newStep = true;
        logger.info("Number of steps are now " + steps.size());
    }

    public void addIngredient(Ingredient i) {
        ingredients.add(i);
    }

    public void addStep(Step i) {
        steps.add(i);
    }

    public void reset() {
        id = null;
        name = null;
        type = null;
        reference = null;
        ingredients.clear();
        steps.clear();
    }

    public void deleteIngredient(int rowIndex) {
        logger.info("Deleting ingredient " + rowIndex);
        ingredients.remove(rowIndex);
    }

    public void deleteStep(int rowIndex) {
        logger.info("Deleting step " + rowIndex);
        steps.remove(rowIndex);
    }

}
