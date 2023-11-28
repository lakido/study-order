package org.study.ui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private final ObservableList<IngredientModel> modelObservableList = FXCollections.observableArrayList();

    private final IngredientDataRepository ingredientDataRepository = initIngredientDataRepository();

    public IngredientsInContextMenuScreenController() throws FailedConnectingException {}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public void setFieldsByRecipeModel(RecipeModel recipeModel) {
        ExtractIngredientListByRecipeIdUseCase extractIngredientListByRecipeIdUseCase = new ExtractIngredientListByRecipeIdUseCase(ingredientDataRepository);
        modelObservableList.addAll(extractIngredientListByRecipeIdUseCase.invoke(recipeModel.getId()).getOrNull());
        fillAndCustomizeTable();
    }

    private void fillAndCustomizeTable() {
        ingredientNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ingredientCaloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
        ingredientWeightColumn.setCellValueFactory(new PropertyValueFactory<>("weight"));
        ingredientRecommendationColumn.setCellValueFactory(new PropertyValueFactory<>("recommendation"));

        ingredientModelTableView.setItems(modelObservableList);
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
