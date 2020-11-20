package com.svalero;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import util.AlertUtils;
import util.R;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class AppController{

    public TextField tfUrl;
    public VBox container;
    public Label lblDirectory;
    public Button btnDir;
    public Button btnAdd;


    @FXML
    public String selectDirectory(ActionEvent event){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(new Stage());
        String dir = selectedDir.getAbsolutePath();
        lblDirectory.setText(dir);
        System.out.println(dir);
        btnDir.setDisable(true);

        return dir;
    }

    @FXML
    public void add(ActionEvent event) {

        String urlValue = tfUrl.getText();
        DownloadController downloadController = new DownloadController(this);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUi("vbox-descarga.fxml"));
            loader.setController(downloadController);
            VBox downloadVBox = loader.load();

            //comprobamos que se ha seleccionado un directorio para depositar la descarga
            //si no se selecciona no se podrá descargar nada
           if (lblDirectory.getText().isEmpty()){
               AlertUtils.showAlert("Selecciona directorio de descarga");
               return;
           }

                downloadController.tfUrl.setText(urlValue);
                downloadController.tfUrl.setDisable(true);

                downloadController.setDownload(event);
                //limpiamos label de añadir descarga
                tfUrl.setText("");

                container.getChildren().add(downloadVBox);

                //comprobamos el valor de setDelete


            Platform.runLater(()->{
            if(downloadController.download.setDelete()){
                //si se ha poinchado en el boton de "eliminar" (event) delete = true y desencadena el metodo
                downloadController.setDelete(event);
                //gestion del evento sobre el boton. es decir, accion que hara el programa al pulsar sobre dicho boton
                downloadController.btnDelete.setOnAction(actionEvent -> container.getChildren().remove(downloadVBox));
            }});
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


}