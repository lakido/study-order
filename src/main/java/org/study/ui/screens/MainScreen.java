package org.study.ui.screens;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.study.data.exceptions.FailedConnectingException;
import org.study.data.exceptions.UnexpectedException;
import org.study.ui.controllers.MainScreenController;

import java.io.IOException;
import java.net.URL;

public class MainScreen extends Application {
    private final double NORMAL_SIZE_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight() * 0.7 ;
    private final double NORMAL_SIZE_WIDTH = Screen.getPrimary().getVisualBounds().getWidth() * 0.7;

    private BorderPane borderPane;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws FailedConnectingException {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/Screens/MainScreen.fxml");
        fxmlLoader.setLocation(xmlUrl);


        Parent root;

        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);

        MainScreenController mainScreenController = fxmlLoader.getController();
        handleWindowResizing(stage, mainScreenController);

        stage.centerOnScreen();
        stage.show();
        centerStageAfterResizing(stage);
    }

    private void centerStageAfterResizing(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        stage.widthProperty().addListener((observable, oldValue, newValue) -> {

            double centerX = screenBounds.getWidth() / 2 - stage.getWidth() / 2;
            double centerY = screenBounds.getHeight() / 2 - stage.getHeight() / 2;

            stage.setX(centerX);
            stage.setY(centerY);
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {

            double centerX = screenBounds.getWidth() / 2 - stage.getWidth() / 2;
            double centerY = screenBounds.getHeight() / 2 - stage.getHeight() / 2;

            stage.setX(centerX);
            stage.setY(centerY);
        });
    }

    private void handleWindowResizing(Stage stage, MainScreenController mainScreenController) {
        stage.addEventHandler(WindowEvent.WINDOW_SHOWING, windowEvent -> {
            stage.setWidth(NORMAL_SIZE_WIDTH);
            stage.setHeight(NORMAL_SIZE_HEIGHT);

            borderPane = mainScreenController.getBorder_pane();

            stage.heightProperty().addListener((observableValue, oldValue, newValue) -> borderPane.setPrefHeight(newValue.doubleValue()));
            stage.widthProperty().addListener((observableValue, oldValue, newValue) -> borderPane.setPrefWidth(newValue.doubleValue()));
        });
    }
}

