package com.svalero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.w3c.dom.Node;


public class DownloadController {
    public TextField tfUrl;
    public Button btnDownload, btnPause, btnResume, btnCancel, btnDelete;
    public ProgressBar pbProgress;
    public Label lblStatus;

    public Download download;

    @FXML
    public void setDownload(ActionEvent event){
            String url = tfUrl.getText();
            download = new Download(url, pbProgress, lblStatus);
            download.start();

            disableDownloadButton();
            enableCancelbutton();
            enablePauseButton();
            disableResumeButton();
    }

    @FXML
    public void setPause(ActionEvent event){
        download.setPause();

        disableDownloadButton();
        disablePauseButton();
        enableCancelbutton();
        enableResumeButton();
    }

    @FXML
    public void setResume(ActionEvent event){
        download.setResume();

        disableDownloadButton();
        disableDownloadButton();
        enablePauseButton();
        enableCancelbutton();
    }

    @FXML
    public void setCancel(ActionEvent event){
        download.setCancel();

        enableDownloadButton();
        disableCancelButton();
        disableResumeButton();
        disablePauseButton();
    }

    @FXML
    public void setDelete(ActionEvent event){
        download.setDelete();

    }



    //métodos de gestión de disponibilidad de botones
    public void disableDownloadButton(){
        btnDownload.setDisable(true);
    }

    public void enableDownloadButton(){
        btnDownload.setDisable(false);
    }

    public void disableCancelButton(){
        btnCancel.setDisable(true);
    }

    public void enableCancelbutton(){
        btnCancel.setDisable(false);
    }

    public void disablePauseButton(){
        btnPause.setDisable(true);
    }

    public void enablePauseButton(){
        btnPause.setDisable(false);
    }

    public void disableResumeButton(){
        btnResume.setDisable(true);
    }

    public void enableResumeButton(){
        btnResume.setDisable(false);
    }
}
