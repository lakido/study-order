package org.study.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.study.domain.models.RecipeModel;
import org.study.domain.usecases.ingredient.ExtractIngredientListByRecipeIdUseCase;
import org.study.domain.usecases.recipe.UpdateRecipeUseCase;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class RecipeEditingController implements Initializable {
    @FXML
    public TextField recipeNameTextField;
    @FXML
    public ComboBox<String> recipeCategoryComboBox;
    @FXML
    public Spinner<Integer> recipePopularitySpinner;
    @FXML
    public Spinner<Integer> recipeAgePreferencesSpinner;
    @FXML
    public Button addIngredientButton;
    @FXML
    public TableView<IngredientModel> tableWithIngredientsToAdd;

    @FXML
    public TableColumn<IngredientModel, String> ingredientNameColumn;
    @FXML
    public TableColumn<IngredientModel, Integer> ingredientCaloriesColumn;
    @FXML
    public TableColumn<IngredientModel, Integer> ingredientWeightColumn;
    @FXML
    public TableColumn<IngredientModel, String> ingredientRecommendationColumn;

    private final ObservableList<IngredientModel> observableList = FXCollections.observableArrayList();
    @FXML
    public Button updateRecipeButton;

    private MainScreenController mainScreenController;

    private RecipeModel recipeModel;

    private final IngredientDataSource ingredientDataSource = IngredientDataSource.getInstance(
            IngredientUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            IngredientDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            IngredientEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            IngredientInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection())
    );

    private final RecipeDataSource recipeDataSource = RecipeDataSource.getInstance(
            RecipeUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            RecipeDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            RecipeEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            RecipeInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection())
    );

    private final IngredientDataRepository ingredientDataRepository = IngredientDataRepository.getInstance(ingredientDataSource);
    private final RecipeDataRepository recipeDataRepository = RecipeDataRepository.getInstance(recipeDataSource);

    public RecipeEditingController() throws FailedConnectingException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleButtonToCreateNewWindowToUpdateIngredientForRecipe(ActionEvent actionEvent) {
    }

    @FXML
    public void handleButtonToUpdateRecipeInDatabase(ActionEvent actionEvent) {
        UpdateRecipeUseCase updateRecipeUseCase = new UpdateRecipeUseCase(recipeDataRepository);
        RecipeModel updatedRecipeModel = new RecipeModel(
                recipeModel.getId(),
                recipeNameTextField.getText(),
                recipeCategoryComboBox.getValue(),
                recipePopularitySpinner.getValue(),
                recipeAgePreferencesSpinner.getValue()
        );

        updateRecipeUseCase.invoke(updatedRecipeModel);
        mainScreenController.refreshTableView();
        Stage stage = (Stage) updateRecipeButton.getScene().getWindow();
        stage.close();
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }

    public void setTableWithIngredientsByRecipeModel() {
        restrictionForTextField();
        recipeNameTextField.setAlignment(Pos.CENTER_LEFT);
        recipeNameTextField.setText(recipeModel.getName());

        recipeCategoryComboBox.setValue(recipeModel.getCategory());

        restrictionForRecipePopularitySpinner();
        restrictionForRecipeAgePreferencesSpinner();
    }

    public void fillAndCustomizeTable() {
        ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        ingredientWeightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        ingredientRecommendationColumn.setCellValueFactory(new PropertyValueFactory<>("recommendation"));

        tableWithIngredientsToAdd.setItems(observableList);
        fillTableViewWithIngredients();
    }

    private void fillTableViewWithIngredients() {
        final ExtractIngredientListByRecipeIdUseCase extractIngredientListByRecipeIdUseCase = new ExtractIngredientListByRecipeIdUseCase(ingredientDataRepository);
        List<IngredientModel> ingredientModelList = new ArrayList<>(extractIngredientListByRecipeIdUseCase.invoke(recipeModel.getId()).getOrNull());
        observableList.addAll(ingredientModelList);
    }

    private void restrictionForRecipePopularitySpinner() {
        SpinnerValueFactory<Integer> recipePopularitySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, recipeModel.getPopularity(), 5);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (Pattern.matches("\\d{0,2}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipePopularitySpinnerValueFactory.getConverter(), recipePopularitySpinnerValueFactory.getValue(), filter);

        recipePopularitySpinner.setValueFactory(recipePopularitySpinnerValueFactory);
        recipePopularitySpinner.getEditor().setTextFormatter(textFormatter);
    }

    private void restrictionForRecipeAgePreferencesSpinner() {
        SpinnerValueFactory<Integer> recipeAgePreferencesSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, recipeModel.getAgePreferences(), 1);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (Pattern.matches("\\d{0,2}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipeAgePreferencesSpinnerValueFactory.getConverter(), recipeAgePreferencesSpinnerValueFactory.getValue(), filter);

        recipeAgePreferencesSpinner.setValueFactory(recipeAgePreferencesSpinnerValueFactory);
        recipeAgePreferencesSpinner.getEditor().setTextFormatter(textFormatter);
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
        recipeNameTextField.setTextFormatter(textFormatter);
    }
}
