package com.svalero;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import util.R;

import java.io.IOException;


public class AppController{

    public TextField tfUrl;
    public VBox container;

    @FXML
    public void add(ActionEvent event) {
        String urlValue = tfUrl.getText();
        DownloadController downloadController = new DownloadController();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUi("vbox-descarga.fxml"));
            loader.setController(downloadController);
            VBox downloadVBox = loader.load();
            if(urlValue.isEmpty()){
                downloadController.tfUrl.setText("Caja sin link de descarga...");
                downloadController.tfUrl.setDisable(true);
            }else{
                downloadController.tfUrl.setText(urlValue);
                downloadController.tfUrl.setDisable(true);
                downloadController.setDownload(event);
                //limpiamos label de añadir descarga
                tfUrl.setText("");

                container.getChildren().add(downloadVBox);

                //comprobamos el valor de setDelete
                if(downloadController.download.setDelete()){
                    //si se ha poinchado en el boton de "eliminar" (event) delete = true y desencadena el metodo
                    downloadController.setDelete(event);
                    //gestion del evento sobre el boton. es decir, accion que hara el programa al pulsar sobre dicho boton
                    downloadController.btnDelete.setOnAction(actionEvent -> container.getChildren().remove(downloadVBox));
                }
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


}