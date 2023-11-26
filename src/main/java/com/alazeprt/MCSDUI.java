package com.alazeprt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MCSDUI extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MCSDUI.class.getResource("MCSDUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 450, 650);
        stage.setTitle("MCServerDownload-GUI (重构版本) v2.0.0");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
