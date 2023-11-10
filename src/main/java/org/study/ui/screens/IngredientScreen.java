package org.study.ui.screens;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.study.ui.controllers.MainScreenController;

import java.io.IOException;
import java.net.URL;

public class IngredientScreen extends Application {

    @FXML
    Text ingredientName;

    @FXML
    Text ingredientCalories;

    @FXML
    Text ingredientWeight;

    @FXML
    Text ingredientRecommendation;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/Screens/IngredientScreen.fxml");
        fxmlLoader.setLocation(xmlUrl);

        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);

        stage.setTitle("Ingredients");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);

        stage.show();
    }
}
