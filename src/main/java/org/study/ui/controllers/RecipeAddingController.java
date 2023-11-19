package org.study.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
import org.study.domain.repository.RelationRepository;
import org.study.domain.usecases.ingredient.ExtractNextAvailableIdForIngredientUseCase;
import org.study.domain.usecases.ingredient.InsertIngredientUseCase;
import org.study.domain.usecases.recipe.ExtractNextAvailableIdForRecipeUseCase;
import org.study.domain.usecases.recipe.InsertRecipeUseCase;
import org.study.domain.usecases.relation.InsertRelationUseCase;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class RecipeAddingController implements Initializable {

    @FXML
    public TextField recipeNameTextField;
    @FXML
    public ComboBox<String> recipeCategoryComboBox;
    @FXML
    public Spinner<Integer> recipePopularitySpinner;
    @FXML
    public Spinner<Integer> recipeAgePreferencesSpinner;
    @FXML
    public Button addRecipeButton;
    @FXML
    public GridPane gridPane;
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

    private RecipeDataRepository recipeDataRepository;

    private IngredientDataRepository ingredientDataRepository;

    private RelationRepository relationRepository;

    private MainScreenController mainScreenController;

    public RecipeAddingController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        restrictionForRecipePopularitySpinner();
        restrictionForRecipeAgePreferencesSpinner();
        restrictionForTextField();
        createAndCustomizeTableView();

    }

    @FXML
    public void handleButtonToCreateNewWindowToInsertNewRecipe(ActionEvent actionEvent) throws UnexpectedException {
        Stage stage = new Stage() ;
        Stage parentStage = (Stage) addIngredientButton.getScene().getWindow();

        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Screens/AddIngredientScreen.fxml"));

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.getCause();
            throw new UnexpectedException();
        }

        IngredientAddingController ingredientAddingController = fxmlLoader.getController();
        ingredientAddingController.setRecipeAddingController(this);

        // here i adjust the size of the adding of ingredient window to the size of the previous window

        stage.setScene(new Scene(root));
        stage.setHeight(parentStage.getHeight());
        stage.setWidth(parentStage.getWidth());
        stage.setTitle("Add ingredient");

        stage.show();
    }

    private void createAndCustomizeTableView() {
        ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        ingredientWeightColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        ingredientRecommendationColumn.setCellValueFactory(new PropertyValueFactory<>("recommendation"));

        tableWithIngredientsToAdd.setItems(observableList);
    }

    public void addIngredientModelToTheListView(IngredientModel ingredientModel) {
        observableList.add(ingredientModel);
    }

    private void restrictionForRecipePopularitySpinner() {
        SpinnerValueFactory<Integer> recipePopularitySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 10, 1);

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
        SpinnerValueFactory<Integer> recipeAgePreferencesSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 99, 18, 1);

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

    @FXML
    public void handleButtonToInsertNewRecipeIntoDatabase(ActionEvent actionEvent) throws FailedConnectingException {
        initRecipeDataRepository();
        initIngredientDataRepository();
        initRelationRepository();
        addRecipe();

        if (mainScreenController != null) {
            mainScreenController.refreshTableView();
        }

        Stage stage = (Stage) addIngredientButton.getScene().getWindow();
        stage.close();
    }

    public void setMainScreenController(MainScreenController mainScreenController) {
        this.mainScreenController = mainScreenController;
    }

    private void initRecipeDataRepository() throws FailedConnectingException {
        RecipeUpdateWorker recipeUpdateWorker = RecipeUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RecipeEntityExtractor recipeEntityExtractor = RecipeEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RecipeInsertWorker recipeInsertWorker = RecipeInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RecipeDeleteWorker recipeDeleteWorker = RecipeDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());

        RecipeDataSource recipeDataSource = RecipeDataSource.getInstance(recipeUpdateWorker, recipeDeleteWorker, recipeEntityExtractor, recipeInsertWorker);
        this.recipeDataRepository = RecipeDataRepository.getInstance(recipeDataSource);
    }

    private void initIngredientDataRepository() throws FailedConnectingException {
        IngredientUpdateWorker ingredientUpdateWorker = IngredientUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        IngredientEntityExtractor ingredientEntityExtractor = IngredientEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        IngredientInsertWorker ingredientInsertWorker = IngredientInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        IngredientDeleteWorker ingredientDeleteWorker = IngredientDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());

        IngredientDataSource ingredientDataSource = IngredientDataSource.getInstance(ingredientUpdateWorker, ingredientDeleteWorker, ingredientEntityExtractor, ingredientInsertWorker);
        this.ingredientDataRepository = IngredientDataRepository.getInstance(ingredientDataSource);
    }

    private void initRelationRepository() throws FailedConnectingException {
        RelationRecordInsertWorker relationRecordInsertWorker = RelationRecordInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());
        RelationRecordExtractor relationRecordExtractor = RelationRecordExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection());

        RelationDataSource relationDataSource = RelationDataSource.getInstance(relationRecordInsertWorker, relationRecordExtractor);
        this.relationRepository = RelationDataRepository.getInstance(relationDataSource);
    }

    private void addRecipe() {
        ExtractNextAvailableIdForRecipeUseCase availableIdForRecipeUseCase = new ExtractNextAvailableIdForRecipeUseCase(recipeDataRepository);
        ExtractNextAvailableIdForIngredientUseCase availableIdForIngredientUseCase = new ExtractNextAvailableIdForIngredientUseCase(ingredientDataRepository);

        InsertRecipeUseCase insertRecipeUseCase = new InsertRecipeUseCase(recipeDataRepository);

        RecipeModel recipeModel = new RecipeModel(
                1,
                recipeNameTextField.getText(),
                recipeCategoryComboBox.getValue(),
                recipePopularitySpinner.getValue(),
                recipeAgePreferencesSpinner.getValue()
        );

        List<IngredientModel> ingredientModelList = new ArrayList<>(observableList);

        int recipeId = availableIdForRecipeUseCase.invoke().getOrNull();
        int ingredientId = availableIdForIngredientUseCase.invoke().getOrNull();

        insertRecipeUseCase.invoke(recipeModel);

        for (IngredientModel ingredient: ingredientModelList
        ) {
            InsertIngredientUseCase insertIngredientUseCase = new InsertIngredientUseCase(ingredientDataRepository);
            InsertRelationUseCase insertRelationUseCase = new InsertRelationUseCase(relationRepository);

            insertIngredientUseCase.invoke(ingredient);
            insertRelationUseCase.invoke(recipeId, ingredientId);
            ingredientId++;
        }
    }
}
