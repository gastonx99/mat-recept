package se.dandel.recipe.web;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
public class RecipesController implements Serializable {
    private static final Logger logger = Logger.getLogger(RecipesController.class.getName());
    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{recipesModel}")
    private RecipesModel recipesModel;

    @ManagedProperty(value = "#{recipesForm}")
    private RecipesForm recipesForm;

    public void setRecipesModel(RecipesModel recipesModel) {
        this.recipesModel = recipesModel;
    }

    public void setRecipesForm(RecipesForm recipesForm) {
        this.recipesForm = recipesForm;
    }

    public void typeChange(AjaxBehaviorEvent e) {
        logger.info("typeChange: " + recipesForm.getSelectedType());
    }

}
