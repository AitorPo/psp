package com.svalero;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download extends Thread{

    private String url;
    private String saveDir;
    private boolean cancel, pause, delete;
    private ProgressBar pbProgress;
    private Label lblStatus;
    private static final int BUFFER_SIZE = 1024;
    private AppController controller;



    public Download(String url, ProgressBar pbProgress, Label lblStatus, String saveDir, AppController controller){
        this.url = url;
        this.pbProgress = pbProgress;
        this.lblStatus = lblStatus;
        this.controller = controller;
        this.saveDir = saveDir;
        cancel = false;
        pause = false;
        delete = false;
    }

    public Download(){}

    public void setPause(){
        pause = true;
    }

    public void setResume(){ pause = false; }

    public void setCancel(){ cancel = true; }

    public boolean setDelete() { delete = true;
    return delete;}

    @Override
    public void run() {

        controller = new AppController();
        ActionEvent event = new ActionEvent();

            try {

                //creamos objeto URL
                URL url = new URL(this.url);
                //abrimos la conexion con internet
                HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                //obtenemos el tamaño del fiichero
                long size = urlConn.getContentLength();
                //recogemos los datos del fichero
                BufferedInputStream bis = new BufferedInputStream(urlConn.getInputStream());
                //File file = new java.io.File(controller.selectDirectory(event) + File.separator + url.getFile());

                String fileName = "";
                String disposition = urlConn.getHeaderField("Content-Disposition");
                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10, disposition.length() - 1);
                    }
                } else {
                    fileName = this.url.substring(this.url.lastIndexOf("/") + 1);
                }

                //System.out.println(fileName);
                String filePath = saveDir + "\\" + fileName;
                //System.out.println(filePath);
                FileOutputStream fos = new FileOutputStream(filePath);
                BufferedOutputStream bos = new BufferedOutputStream(fos, BUFFER_SIZE);
                byte[] buffer = new byte[BUFFER_SIZE];
                //System.out.println(size);
                long downloaded = (long) 0.00;
                int ridden = 0;
                double percent = 0.00;

                //lblStatus.setText("Descargando: " + size + " Kbts");
                while ((ridden = bis.read(buffer, 0, BUFFER_SIZE)) >= 0) {
                    bos.write(buffer, 0, ridden);
                    downloaded += ridden;
                    percent = (downloaded * 100) / size;
                    long minus = size - downloaded;
                    double dataToFullDownload = (double) minus / 1000000;
                    String percentFormat = String.format("%.2f", percent);
                    String dataToFullDownloadFormat = String.format("%.2f", dataToFullDownload);
                    //System.out.println("down    " + downloaded );
                                Platform.runLater(()-> {
                                            pbProgress.setProgress(ProgressBar.BASELINE_OFFSET_SAME_AS_HEIGHT);
                                            lblStatus.setText("Tamaño total del archivo: "
                                                    + (size / 1000000) + " MB. " + "Descargado "
                                                    + percentFormat + "% del archivo. " +
                                                    "Restantes --> " + dataToFullDownloadFormat + " MB.");
                                        });
                    //cuando la descarga se completa mostramos el mensaje de completada
                    if (dataToFullDownload == 0) {
                        Platform.runLater(() ->lblStatus.setText("Descarga completada"));
                    }
                    //si la paramos mostramos el mensaje de cancelada
                    if (cancel) {
                        Platform.runLater(() ->
                                lblStatus.setText("Descarga cancelada"));
                        break;
                    }

                    if (pause) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException interruptedException) {
                            pause = false;
                        }
                        continue;
                    }
                }
                bos.close();
                bis.close();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            Platform.runLater(()-> pbProgress.setProgress(0));
    }
}
