package org.study.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.changing.IngredientUpdateWorker;
import org.study.data.operations.changing.RecipeUpdateWorker;
import org.study.data.operations.deletion.IngredientDeleteWorker;
import org.study.data.operations.deletion.RecipeDeleteWorker;
import org.study.data.operations.extraction.IngredientEntityExtractor;
import org.study.data.operations.extraction.RecipeEntityExtractor;
import org.study.data.operations.extraction.RelationRecordExtractor;
import org.study.data.operations.inserting.IngredientInsertWorker;
import org.study.data.operations.inserting.RecipeInsertWorker;
import org.study.data.operations.inserting.RelationRecordInsertWorker;
import org.study.data.repository.IngredientDataRepository;
import org.study.data.repository.RecipeDataRepository;
import org.study.data.repository.RelationDataRepository;
import org.study.data.sources.ingredient.IngredientDataSource;
import org.study.data.sources.recipe.RecipeDataSource;
import org.study.data.sources.relation.RelationDataSource;
import org.study.domain.models.IngredientModel;
import org.study.domain.models.RecipeModel;
import org.study.domain.usecases.ingredient.ExtractIngredientListByRecipeIdUseCase;
import org.study.domain.usecases.ingredient.ExtractNextAvailableIdForIngredientUseCase;
import org.study.domain.usecases.ingredient.InsertIngredientUseCase;
import org.study.domain.usecases.recipe.UpdateRecipeUseCase;
import org.study.domain.usecases.relation.InsertRelationUseCase;
import org.study.ui.screens.EditingIngredientScreen;

import java.io.IOException;
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

    private final IngredientDataRepository ingredientDataRepository = initIngredientDataRepository();
    private final RecipeDataRepository recipeDataRepository = initRecipeDataRepository();
    private final RelationDataRepository relationDataRepository = initRelationDataRepository();

    public RecipeEditingController() throws FailedConnectingException {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleButtonToCreateNewWindowToUpdateIngredientForRecipe() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ScreenForEditingIngredients.fxml"));
        Stage parentStage = (Stage) addIngredientButton.getScene().getWindow();

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new UnexpectedException();
        }

        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Add Ingredient");
        stage.setScene(scene);

        stage.setWidth(parentStage.getWidth());
        stage.setHeight(parentStage.getHeight());

        IngredientEditingController ingredientEditingController = fxmlLoader.getController();
        ingredientEditingController.setRecipeEditingController(this);

        EditingIngredientScreen editingIngredientScreen = new EditingIngredientScreen();
        editingIngredientScreen.start(stage);
    }

    @FXML
    public void handleButtonToUpdateRecipeInDatabase() {
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

        ExtractNextAvailableIdForIngredientUseCase extractNextAvailableIdForIngredientUseCase = new ExtractNextAvailableIdForIngredientUseCase(ingredientDataRepository);
        int nextIngredientId = extractNextAvailableIdForIngredientUseCase.invoke().getOrNull();

        List<IngredientModel> listWithNewIngredients = observableList.stream().filter(ingredientModel -> ingredientModel.getId() == 1).toList();

        listWithNewIngredients.forEach(ingredientModel -> {
            InsertIngredientUseCase insertIngredientUseCase = new InsertIngredientUseCase(ingredientDataRepository);
            insertIngredientUseCase.invoke(ingredientModel);
        });

        for (IngredientModel ignored : listWithNewIngredients
             ) {
            InsertRelationUseCase insertRelationUseCase = new InsertRelationUseCase(relationDataRepository);
            insertRelationUseCase.invoke(recipeModel.getId(), nextIngredientId++);
        }

        stage.close();
    }

    @FXML
    public void editIngredientInContextMenu() {
    }

    @FXML
    public void deleteIngredientInContextMenu() {
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

    public void insertIngredientModelToListView(IngredientModel ingredientModel) {
        observableList.add(ingredientModel);
        tableWithIngredientsToAdd.refresh();
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
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

    private RelationDataRepository initRelationDataRepository() throws FailedConnectingException {
        RelationRecordInsertWorker relationRecordInsertWorker = RelationRecordInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RelationRecordExtractor relationRecordExtractor = RelationRecordExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());

        RelationDataSource relationDataSource = RelationDataSource.getInstance(relationRecordInsertWorker, relationRecordExtractor);
        return RelationDataRepository.getInstance(relationDataSource);
    }
}
