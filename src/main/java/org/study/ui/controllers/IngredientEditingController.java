package org.study.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.operations.changing.IngredientUpdateWorker;
import org.study.data.operations.changing.RecipeUpdateWorker;
import org.study.data.operations.deletion.IngredientDeleteWorker;
import org.study.data.operations.deletion.RecipeDeleteWorker;
import org.study.data.operations.extraction.IngredientEntityExtractor;
import org.study.data.operations.extraction.RecipeEntityExtractor;
import org.study.data.operations.inserting.IngredientInsertWorker;
import org.study.data.operations.inserting.RecipeInsertWorker;
import org.study.data.repository.IngredientDataRepository;
import org.study.data.repository.RecipeDataRepository;
import org.study.data.sources.ingredient.IngredientDataSource;
import org.study.data.sources.recipe.RecipeDataSource;
import org.study.domain.models.IngredientModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class IngredientEditingController implements Initializable {

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
    @FXML
    private RecipeEditingController recipeEditingController;

    private final IngredientDataRepository ingredientDataRepository = initIngredientDataRepository();
    private final RecipeDataRepository recipeDataRepository = initRecipeDataRepository();

    public IngredientEditingController() throws FailedConnectingException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setRestrictions();
    }

    @FXML
    public void editIngredientWithSpecifiedParametersAsRecipePart() {
        IngredientModel ingredientModel = new IngredientModel(
                1,
                ingredientNameTextField.getText(),
                ingredientCaloriesSpinner.getValue(),
                ingredientWeightSpinner.getValue(),
                ingredientRecommendationComboBox.getValue()
        );

        recipeEditingController.insertIngredientModelToListView(ingredientModel);

        Stage stage = (Stage) confirmAddingIngredientButton.getScene().getWindow();
        stage.close();
    }

    public void setRecipeEditingController(RecipeEditingController recipeEditingController) {
        this.recipeEditingController = recipeEditingController;
    }

    private void setRestrictions() {
        restrictionForIngredientCaloriesSpinner();
        restrictionForIngredientWeightSpinner();
        restrictionForTextField();
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

    private RecipeDataRepository initRecipeDataRepository() throws FailedConnectingException {
        RecipeUpdateWorker recipeUpdateWorker = RecipeUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RecipeEntityExtractor recipeEntityExtractor = RecipeEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RecipeInsertWorker recipeInsertWorker = RecipeInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RecipeDeleteWorker recipeDeleteWorker = RecipeDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());

        RecipeDataSource recipeDataSource = RecipeDataSource.getInstance(recipeUpdateWorker, recipeDeleteWorker, recipeEntityExtractor, recipeInsertWorker);
        return RecipeDataRepository.getInstance(recipeDataSource);
    }

    private IngredientDataRepository initIngredientDataRepository() throws FailedConnectingException {
        IngredientUpdateWorker ingredientUpdateWorker = IngredientUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        IngredientEntityExtractor ingredientEntityExtractor = IngredientEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        IngredientInsertWorker ingredientInsertWorker = IngredientInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        IngredientDeleteWorker ingredientDeleteWorker = IngredientDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());

        IngredientDataSource ingredientDataSource = IngredientDataSource.getInstance(ingredientUpdateWorker, ingredientDeleteWorker, ingredientEntityExtractor, ingredientInsertWorker);
        return IngredientDataRepository.getInstance(ingredientDataSource);
    }
}
