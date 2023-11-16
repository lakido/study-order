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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.study.data.exceptions.UnexpectedException;
import org.study.domain.models.IngredientModel;

import java.io.IOException;
import java.net.URL;
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

    public RecipeAddingController() {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        restrictionForRecipePopularitySpinner();
        restrictionForRecipeAgePreferencesSpinner();
        restrictionForTextField();
        createAndCustomizeTableView();

    }

    @FXML
    public void handleButtonToCreateNewWindow(ActionEvent actionEvent) throws UnexpectedException {
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
}
