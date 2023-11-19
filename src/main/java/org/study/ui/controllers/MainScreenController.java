package org.study.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.UnexpectedException;
import org.study.data.operations.changing.RecipeUpdateWorker;
import org.study.data.operations.deletion.RecipeDeleteWorker;
import org.study.data.operations.extraction.RecipeEntityExtractor;
import org.study.data.operations.inserting.RecipeInsertWorker;
import org.study.data.repository.RecipeDataRepository;
import org.study.data.sources.recipe.RecipeDataSource;
import org.study.domain.models.RecipeModel;
import org.study.domain.repository.RecipeRepository;
import org.study.domain.usecases.recipe.ExtractRecipeListOfFirstRecordsUseCase;
import org.study.ui.screens.IngredientsInContextMenuScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class MainScreenController implements Initializable {

    private final ObservableList<RecipeModel> recipeData = FXCollections.observableArrayList();
    @FXML
    public Spinner<Integer> popularitySpinner;
    @FXML
    public ComboBox<String> categoryComboBox;
    @FXML
    public Spinner<Integer> preferredAgeSpinner;
    @FXML
    public Spinner<Integer> caloriesSpinner;

    @FXML
    private VBox menuVBoxMainScreen;

    @FXML
    private TableView<RecipeModel> startTableWithRecipes;

    @FXML
    private TableColumn<RecipeModel, String> recipeNameColumn;

    @FXML
    private TableColumn<RecipeModel, String> recipeCategoryColumn;

    @FXML
    private TableColumn<RecipeModel, Integer> recipePopularityColumn;

    @FXML
    private TableColumn<RecipeModel, Integer> recipeAgePreferencesColumn;

    @FXML
    private BorderPane borderPaneMainScreen;

    private final RecipeDataSource recipeDataSource = RecipeDataSource.getInstance(
            RecipeUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            RecipeDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            RecipeEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            RecipeInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection())
    );

    private final RecipeRepository recipeRepository = RecipeDataRepository.getInstance(recipeDataSource);

    @FXML
    public void handleRecipeAddButtonClick() throws Exception {
        Stage stage = new Stage();

        Parent root;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Screens/AddRecipeScreen.fxml"));

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.getCause();
            throw new UnexpectedException();
        }

        stage.setScene(new Scene(root));

        stage.setTitle("Add recipe");

//      installing the controller of the parent window on the child window at the place where the new window is created
        RecipeAddingController recipeAddingController = fxmlLoader.getController();
        recipeAddingController.setMainScreenController(this);

        stage.show();
    }
    public MainScreenController() throws FailedConnectingException {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRestrictions();
        customizeVBox();

        ExtractRecipeListOfFirstRecordsUseCase extractRecipeListOfFirstRecordsUseCase = new ExtractRecipeListOfFirstRecordsUseCase(recipeRepository);
        recipeData.addAll(extractRecipeListOfFirstRecordsUseCase.invoke(100).getOrNull());
        createAndCustomizeTableView();

        startTableWithRecipes.setRowFactory(recipeModelTableView -> {
            TableRow<RecipeModel> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (!row.isEmpty() && mouseEvent.getClickCount() == 2) {
                    handleDoubleClickToCreateWindowWithIngredients(row);
                }
            });
            return row;
        });
    }

    public BorderPane getBorder_pane() {
        return borderPaneMainScreen;
    }

    public void refreshTableView() {
        recipeData.clear();
        startTableWithRecipes.refresh();

        ExtractRecipeListOfFirstRecordsUseCase extractRecipeListOfFirstRecordsUseCase = new ExtractRecipeListOfFirstRecordsUseCase(recipeRepository);
        recipeData.addAll(extractRecipeListOfFirstRecordsUseCase.invoke(recipeData.size()).getOrNull());
        startTableWithRecipes.refresh();
    }

    private void createAndCustomizeTableView() {
        startTableWithRecipes.prefWidthProperty().bind(borderPaneMainScreen.widthProperty().multiply(0.43));

        recipeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        recipeCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        recipePopularityColumn.setCellValueFactory(new PropertyValueFactory<>("popularity"));
        recipeAgePreferencesColumn.setCellValueFactory(new PropertyValueFactory<>("agePreferences"));

        startTableWithRecipes.setItems(recipeData);
    }

    private void customizeVBox() {
        menuVBoxMainScreen.prefWidthProperty().bind(borderPaneMainScreen.widthProperty().multiply(0.3));
        VBox.setVgrow(menuVBoxMainScreen, Priority.ALWAYS);
    }

    private void handleDoubleClickToCreateWindowWithIngredients(TableRow<RecipeModel> row) {
        RecipeModel recipeModel = row.getItem();

        Parent root;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Screens/IngredientsInContextMenuScreen.fxml"));

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IngredientsInContextMenuScreenController ingredientsInContextMenuScreenController = fxmlLoader.getController();
        ingredientsInContextMenuScreenController.setRecipeModel(recipeModel);

        ingredientsInContextMenuScreenController.setFieldsByRecipeModel(recipeModel);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.setTitle("Ingredients");
        stage.initModality(Modality.APPLICATION_MODAL);

        openIngredientWindow(stage);
    }

    private void openIngredientWindow (Stage stage) {
        IngredientsInContextMenuScreen ingredientsInContextMenuScreen = new IngredientsInContextMenuScreen();
        try {
            ingredientsInContextMenuScreen.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void setRestrictions() {
        restrictionForRecipePopularitySpinner();
        restrictionForRecipeAgeSpinner();
        restrictionForRecipeCaloriesSpinner();
    }

    private void restrictionForRecipePopularitySpinner() {
        SpinnerValueFactory<Integer> recipePopularitySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 50, 5);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (Pattern.matches("\\d{0,3}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipePopularitySpinnerValueFactory.getConverter(), recipePopularitySpinnerValueFactory.getValue(), filter);

        popularitySpinner.setValueFactory(recipePopularitySpinnerValueFactory);
        popularitySpinner.getEditor().setTextFormatter(textFormatter);
    }

    private void restrictionForRecipeAgeSpinner() {
        SpinnerValueFactory<Integer> recipePopularitySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 18, 1);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (Pattern.matches("\\d{0,3}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipePopularitySpinnerValueFactory.getConverter(), recipePopularitySpinnerValueFactory.getValue(), filter);

        preferredAgeSpinner.setValueFactory(recipePopularitySpinnerValueFactory);
        preferredAgeSpinner.getEditor().setTextFormatter(textFormatter);
    }

    private void restrictionForRecipeCaloriesSpinner() {
        SpinnerValueFactory<Integer> recipePopularitySpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 20000, 200, 50);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();

            if (Pattern.matches("\\d{0,3}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipePopularitySpinnerValueFactory.getConverter(), recipePopularitySpinnerValueFactory.getValue(), filter);

        caloriesSpinner.setValueFactory(recipePopularitySpinnerValueFactory);
        caloriesSpinner.getEditor().setTextFormatter(textFormatter);
    }
}