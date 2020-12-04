package com.svalero;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.AlertUtils;
import util.R;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


public class AppController{
    private static final Logger logger = LogManager.getLogger(AppController.class);

    public TextField tfUrl;
    public VBox container;
    public Label lblDirectory;
    public Button btnDir;
    private List<DownloadController> downloadControllers;
    private List<String> urlList;
    public VBox downloadVBox;
    public ComboBox<String> cbHistory;
    //private List<VBox> downloads;

    private DownloadController downloadController;


    public AppController(){
        downloadControllers = new ArrayList<>();
        urlList = new ArrayList<>();
        //downloads = new ArrayList<>();
    }

    public void setLogger(String url) {

    logger.trace(url);
    }

    public void setHistory(String url){
        FileWriter fw = null;
        PrintWriter pw = null;

        try {
            fw = new FileWriter("dlc.txt", true);
            pw = new PrintWriter(fw);


                pw.println(url);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
     @FXML
    public void showHistory(ActionEvent event){

      /* File log = new File("logs.log");

       try{
           List<String> urls = Files.readAllLines(log.toPath());
           urls.forEach(this::setLogger);

       }catch (IOException ioe){
           ioe.printStackTrace();
       }*/

        File history = new File("dlc.txt");
         try {
             List<String> historyUrl = Files.readAllLines(history.toPath());
             cbHistory.setItems(FXCollections.observableList(historyUrl));
         } catch (IOException e) {
             e.printStackTrace();
         }

     }

    @FXML
    public String selectDirectory(ActionEvent event){

        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDir = directoryChooser.showDialog(tfUrl.getScene().getWindow());
        String dir = selectedDir.getAbsolutePath();
        lblDirectory.setText(dir);
        System.out.println(dir);
        btnDir.setDisable(true);

        return dir;
    }

    public void loadMenu(AppController controller) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUi("scrollpane-downloader-add-screen.fxml"));
        loader.setController(this);
        VBox vBox = loader.load();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBox);

        Stage stage = new Stage();
        Scene scene = new Scene(scrollPane, 800, 800);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void add(ActionEvent event) {

        String urlValue = tfUrl.getText();
        downloadController = new DownloadController(this);
        downloadControllers.add(downloadController);
        System.out.println("Lista de controladores: " + downloadControllers);
        urlList.add(urlValue);
        System.out.println("\n Lista de url: " + urlList);
        setHistory(urlValue);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(R.getUi("vbox-descarga.fxml"));
            loader.setController(downloadController);
            downloadVBox = loader.load();

            //comprobamos que se ha seleccionado un directorio para depositar la descarga
            //si no se selecciona no se podrá descargar nada
           if (lblDirectory.getText().isEmpty()){
               AlertUtils.showAlert("Selecciona directorio de descarga");
               return;
           }

                downloadController.tfUrl.setText(urlValue);
                downloadController.tfUrl.setDisable(true);
                this.setLogger(urlValue);
                downloadController.setDownload(event);
                //limpiamos label de añadir descarga
                tfUrl.clear();

                //downloads.add(downloadVBox);
            Platform.runLater(()-> container.getChildren().add(downloadVBox));
                //container.getChildren().add(downloadVBox);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }


    @FXML
    public void stopAllDownloads(ActionEvent event) throws IOException {

        Platform.runLater(()-> {
            for (DownloadController downloadController : downloadControllers){


            }
            //downloads.clear();

            downloadControllers.clear();
        System.out.println("Lista vacía " + downloadControllers);

        //container.getChildren().clear();
        //loadMenu(this);
            });
        }

}