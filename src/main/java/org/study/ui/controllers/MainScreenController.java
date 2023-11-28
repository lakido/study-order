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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
import org.study.data.operations.inserting.IngredientInsertWorker;
import org.study.data.operations.inserting.RecipeInsertWorker;
import org.study.data.repository.IngredientDataRepository;
import org.study.data.repository.RecipeDataRepository;
import org.study.data.sources.ingredient.IngredientDataSource;
import org.study.data.sources.recipe.RecipeDataSource;
import org.study.domain.models.IngredientModel;
import org.study.domain.models.RecipeModel;
import org.study.domain.repository.IngredientRepository;
import org.study.domain.repository.RecipeRepository;
import org.study.domain.usecases.ingredient.ExtractIngredientListByRecipeIdUseCase;
import org.study.domain.usecases.recipe.DeleteRecipeByNameUseCase;
import org.study.domain.usecases.recipe.ExtractRecipeListOfFirstRecordsUseCase;
import org.study.domain.usecases.recipe.ExtractRecipeListUseCase;
import org.study.ui.screens.EditingRecipeScreen;
import org.study.ui.screens.InformationAboutIngredientScreen;

import java.io.IOException;
import java.net.URL;
import java.util.*;
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

    private final RecipeRepository recipeRepository = initRecipeDataRepository();
    private final IngredientRepository ingredientRepository = initIngredientDataRepository();

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

    @FXML
    public void handleRecipeAddButtonClick() throws Exception {
        Stage stage = new Stage();

        Parent root;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ScreenForAddingRecipes.fxml"));

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

    @FXML
    public void findARecipes() {
        Integer popularity = popularitySpinner.getValue();
        String category = categoryComboBox.getValue();
        Integer preferredAge = preferredAgeSpinner.getValue();
        Integer calories = caloriesSpinner.getValue();

        ExtractRecipeListUseCase extractRecipeListUseCase = new ExtractRecipeListUseCase(recipeRepository);
        List<RecipeModel> recipeModelList = new ArrayList<>(extractRecipeListUseCase.invoke(category, popularity, preferredAge).getOrNull());

        Map<RecipeModel, List<IngredientModel>> recipeModelListMap = new HashMap<>();

        for(RecipeModel recipeModel: recipeModelList) {
            ExtractIngredientListByRecipeIdUseCase extractIngredientListByRecipeIdUseCase = new ExtractIngredientListByRecipeIdUseCase(ingredientRepository);
            recipeModelListMap.put(recipeModel, extractIngredientListByRecipeIdUseCase.invoke(recipeModel.getId()).getOrNull());
        }

        Iterator<Map.Entry<RecipeModel, List<IngredientModel>>> iterator = recipeModelListMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<RecipeModel, List<IngredientModel>> entry = iterator.next();

            List<IngredientModel> list = new ArrayList<>(entry.getValue());

            int sumOfCalories = 0;

            for(IngredientModel ingredientModel: list) {
                sumOfCalories += ingredientModel.getCalories();
            }

            if (sumOfCalories > calories) iterator.remove();
        }

        recipeData.clear();
        recipeData.addAll(recipeModelListMap.keySet());
        startTableWithRecipes.refresh();
    }

    @FXML
    public void deleteRecipeInContextMenu() {
        RecipeModel recipeModel = startTableWithRecipes.getSelectionModel().getSelectedItem();

        DeleteRecipeByNameUseCase deleteRecipeByNameUseCase = new DeleteRecipeByNameUseCase(recipeRepository);
        deleteRecipeByNameUseCase.invoke(recipeModel.getName());

        recipeData.remove(recipeModel);
        startTableWithRecipes.refresh();
    }

    @FXML
    public void editRecipeInContextMenu() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ScreenForEditingRecipes.fxml"));
        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Edit Recipe");

        RecipeEditingController recipeEditingController = fxmlLoader.getController();
        recipeEditingController.setMainScreenController(this);

        EditingRecipeScreen editingRecipeScreen = new EditingRecipeScreen();
        editingRecipeScreen.start(stage);

        RecipeModel recipeModel = startTableWithRecipes.getSelectionModel().getSelectedItem();
        recipeEditingController.setRecipeModel(recipeModel);
        recipeEditingController.fillAndCustomizeTable();

        recipeEditingController.setTableWithIngredientsByRecipeModel();
    }

    public void refreshTableView() {
        recipeData.clear();
        startTableWithRecipes.refresh();

        ExtractRecipeListOfFirstRecordsUseCase extractRecipeListOfFirstRecordsUseCase = new ExtractRecipeListOfFirstRecordsUseCase(recipeRepository);
        recipeData.addAll(extractRecipeListOfFirstRecordsUseCase.invoke(50).getOrNull());
        startTableWithRecipes.refresh();
    }

    public BorderPane getBorder_pane() {
        return borderPaneMainScreen;
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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/screens/ScreenWithInformationAboutIngredient.fxml"));

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        IngredientsInContextMenuScreenController ingredientsInContextMenuScreenController = fxmlLoader.getController();

        ingredientsInContextMenuScreenController.setFieldsByRecipeModel(recipeModel);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.setTitle("Ingredients");
        stage.initModality(Modality.APPLICATION_MODAL);

        openIngredientWindow(stage);
    }

    private void openIngredientWindow (Stage stage) {
        InformationAboutIngredientScreen informationAboutIngredientScreen = new InformationAboutIngredientScreen();
        try {
            informationAboutIngredientScreen.start(stage);
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

            if (Pattern.matches("\\d{0,5}", newText)) {
                return change;
            } else {
                return null;
            }
        };

        TextFormatter<Integer> textFormatter = new TextFormatter<>(recipePopularitySpinnerValueFactory.getConverter(), recipePopularitySpinnerValueFactory.getValue(), filter);

        caloriesSpinner.setValueFactory(recipePopularitySpinnerValueFactory);
        caloriesSpinner.getEditor().setTextFormatter(textFormatter);
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