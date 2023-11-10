package org.study.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import org.study.domain.models.RecipeModel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IngredientScreenController implements Initializable {

    @FXML
    Text ingredientName;
    @FXML
    Text ingredientCalories;
    @FXML
    Text ingredientWeight;
    @FXML
    Text ingredientRecommendation;

    private MainScreenController mainScreenController;

    private RecipeModel recipeModel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (recipeModel != null) {
            ingredientCalories.setText("text" + recipeModel.getCategory());
        }
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }
}
