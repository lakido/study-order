package org.study.ui.screens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.study.data.exceptions.UnexpectedException;
import org.study.ui.controllers.MainScreenController;
import org.study.ui.controllers.RecipeAddingController;

import java.io.IOException;

public class RecipeAddingScreen extends Application {

    private final double NORMAL_SIZE_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight() * 0.7 ;
    private final double NORMAL_SIZE_WIDTH = Screen.getPrimary().getVisualBounds().getWidth() * 0.7;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Screens/AddRecipeScreen.fxml"));

        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);

        handleWindowResizing(stage);
        stage.centerOnScreen();

        stage.show();
        centerStageAfterResizing(stage);
    }

    private void handleWindowResizing(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Screens/AddRecipeScreen.fxml"));
        RecipeAddingController recipeAddingController = fxmlLoader.getController();

        stage.addEventHandler(WindowEvent.WINDOW_SHOWING, windowEvent -> {
            stage.setWidth(NORMAL_SIZE_WIDTH);
            stage.setHeight(NORMAL_SIZE_HEIGHT);

            GridPane gridPane = recipeAddingController.getGridPane();

            stage.heightProperty().addListener((observableValue, oldValue, newValue) -> gridPane.setPrefHeight(newValue.doubleValue()));
            stage.widthProperty().addListener((observableValue, oldValue, newValue) -> gridPane.setPrefWidth(newValue.doubleValue()));

            stage.centerOnScreen();
        });
    }

    private void centerStageAfterResizing(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        stage.widthProperty().addListener((observable, oldValue, newValue) -> {

            double centerX = screenBounds.getWidth() / 2 - stage.getWidth() / 2;
            double centerY = screenBounds.getHeight() / 2 - stage.getHeight() / 2;

            stage.setX(centerX);
            stage.setY(centerY);

            stage.centerOnScreen();
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {

            double centerX = screenBounds.getWidth() / 2 - stage.getWidth() / 2;
            double centerY = screenBounds.getHeight() / 2 - stage.getHeight() / 2;

            stage.setX(centerX);
            stage.setY(centerY);

            stage.centerOnScreen();
        });
    }

}
