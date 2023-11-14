package org.study.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.study.data.connection.ConnectionDatabaseSingleton;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.operations.changing.IngredientUpdateWorker;
import org.study.data.operations.deletion.IngredientDeleteWorker;
import org.study.data.operations.extraction.IngredientEntityExtractor;
import org.study.data.operations.inserting.IngredientInsertWorker;
import org.study.data.repository.IngredientDataRepository;
import org.study.data.sources.ingredient.IngredientDataSource;
import org.study.domain.models.IngredientModel;
import org.study.domain.models.RecipeModel;
import org.study.domain.usecases.ingredient.ExtractIngredientListByRecipeIdUseCase;

import java.net.URL;
import java.util.ResourceBundle;

public class IngredientsInContextMenuScreenController implements Initializable {

    @FXML
    private TableView<IngredientModel> ingredientModelTableView;
    @FXML
    private TableColumn<IngredientModel, String> ingredientNameColumn;
    @FXML
    private TableColumn<IngredientModel, Integer> ingredientCaloriesColumn;
    @FXML
    private TableColumn<IngredientModel, Integer> ingredientWeightColumn;
    @FXML
    private TableColumn<IngredientModel, String> ingredientRecommendationColumn;
    @FXML
    private Text ingredientName;
    @FXML
    private Text ingredientCalories;
    @FXML
    private Text ingredientWeight;
    @FXML
    private Text ingredientRecommendation;

    private RecipeModel recipeModel;

    private final ObservableList<IngredientModel> modelObservableList = FXCollections.observableArrayList();

    private final IngredientDataSource ingredientDataSource = IngredientDataSource.getInstance(
            IngredientUpdateWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            IngredientDeleteWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            IngredientEntityExtractor.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection()),
            IngredientInsertWorker.getInstance(ConnectionDatabaseSingleton.getInstance().getConnection())
    );

    private final IngredientDataRepository ingredientDataRepository = IngredientDataRepository.getInstance(ingredientDataSource);

    private final ExtractIngredientListByRecipeIdUseCase extractIngredientListByRecipeIdUseCase = new ExtractIngredientListByRecipeIdUseCase(ingredientDataRepository);

    public IngredientsInContextMenuScreenController() throws FailedConnectingException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setIngredientName(String ingredientName) {
//        this.ingredientName.setText(ingredientName);
        System.out.println(recipeModel.getId());
    }

    public void setIngredientCalories(String ingredientCalories) {
        this.ingredientCalories.setText(ingredientCalories);
    }

    public void setIngredientWeight(String ingredientWeight) {
        this.ingredientWeight.setText(ingredientWeight);
    }

    public void setIngredientRecommendation(String ingredientRecommendation) {
        this.ingredientRecommendation.setText(ingredientRecommendation);
    }

    public void setFieldsByRecipeModel(RecipeModel recipeModel) {
        modelObservableList.addAll(extractIngredientListByRecipeIdUseCase.invoke(recipeModel.getId()).getOrNull());
        fillAndCustomizeTable();
    }

    public void setFieldsByIngredientModel(IngredientModel ingredientModel) {

    }

    public void setRecipeModel(RecipeModel recipeModel) {
        this.recipeModel = recipeModel;
    }

    private void fillAndCustomizeTable() {
        ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        ingredientWeightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        ingredientRecommendationColumn.setCellValueFactory(new PropertyValueFactory<>("recommendation"));

        ingredientModelTableView.setItems(modelObservableList);
    }
}
