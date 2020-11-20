package com.svalero;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import util.AlertUtils;
import util.R;

import java.io.File;


public class App extends Application {
private AppController controller;
private ActionEvent event;
private Button btnAdd;
private Label lblDirectory;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {


            controller = new AppController();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUi("scrollpane-downloader-add-screen.fxml"));
            loader.setController(controller);
            VBox vBox = loader.load();
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vBox);


            Scene scene = new Scene(scrollPane, 800, 800);
            stage.setScene(scene);
            stage.show();
        }




    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
