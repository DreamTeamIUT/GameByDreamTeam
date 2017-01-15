package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.nashorn.internal.runtime.Debug;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {


    public static Stage primaryStage;
    public static Parent root;

    @Override
    public void start(Stage primaryStage1) throws Exception {


        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        root = loader.load();
        Controller c = loader.getController();
        c.setStage(primaryStage1);

        primaryStage1.setTitle("PackageCreator");
        primaryStage1.setScene(new Scene(root, 600, 500, Color.BLACK));
        primaryStage1.show();

        primaryStage = primaryStage1;

    }


    public static void main(String[] args) {
        launch(args);
    }



}


