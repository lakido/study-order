package org.study.ui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.study.domain.models.IngredientModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class IngredientAddingController implements Initializable {
    @FXML
    public GridPane gridPaneIngredientAdding;
    @FXML
    public TextField ingredientNameTextField;
    @FXML
    public ComboBox<String> ingredientRecommendationComboBox;
    @FXML
    public Spinner<Integer> ingredientWeightSpinner;
    @FXML
    public Spinner<Integer> ingredientCaloriesSpinner;
    @FXML
    public Button confirmAddingIngredientButton;

    private RecipeAddingController recipeAddingController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restrictionForIngredientWeightSpinner();
        restrictionForIngredientCaloriesSpinner();
        restrictionForTextField();
    }

    @FXML
    public void addIngredientWithSpecifiedParametersAsRecipePart(ActionEvent actionEvent){
        IngredientModel ingredientModel = new IngredientModel(
                1,
                ingredientNameTextField.getText(),
                ingredientCaloriesSpinner.getValue(),
                ingredientWeightSpinner.getValue(),
                ingredientRecommendationComboBox.getValue()
        );

        RecipeAddingController recipeAddingController = getRecipeAddingController();
        recipeAddingController.addIngredientModelToTheListView(ingredientModel);

        Stage stage = (Stage) confirmAddingIngredientButton.getScene().getWindow();
        stage.close();
    }

    public void setRecipeAddingController(RecipeAddingController recipeAddingController) {
        this.recipeAddingController = recipeAddingController;
    }

    public RecipeAddingController getRecipeAddingController() {
        return recipeAddingController;
    }

    private void restrictionForIngredientWeightSpinner() {
        SpinnerValueFactory<Integer> recipePopularitySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 10000, 300, 100);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (Pattern.matches("\\d{0,5}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipePopularitySpinnerValueFactory.getConverter(), recipePopularitySpinnerValueFactory.getValue(), filter);

        ingredientWeightSpinner.setValueFactory(recipePopularitySpinnerValueFactory);
        ingredientWeightSpinner.getEditor().setTextFormatter(textFormatter);
    }

    private void restrictionForIngredientCaloriesSpinner() {
        SpinnerValueFactory<Integer> recipeAgePreferencesSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000, 200, 50);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (Pattern.matches("\\d{0,5}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipeAgePreferencesSpinnerValueFactory.getConverter(), recipeAgePreferencesSpinnerValueFactory.getValue(), filter);

        ingredientCaloriesSpinner.setValueFactory(recipeAgePreferencesSpinnerValueFactory);
        ingredientCaloriesSpinner.getEditor().setTextFormatter(textFormatter);
    }

    private void restrictionForTextField() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (newText.matches("[^0-9!@#$%^&*()]{0,100}")) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        ingredientNameTextField.setTextFormatter(textFormatter);
    }
}
