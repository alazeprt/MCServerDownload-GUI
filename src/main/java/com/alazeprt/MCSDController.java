package com.alazeprt;

import com.alazeprt.servers.Server;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.collections.FXCollections.observableList;

public class MCSDController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private Label versionLabel;

    @FXML
    private Label serverLabel;

    @FXML
    private Label mcVerLabel;

    @FXML
    private Label getMcVersLavel;

    @FXML
    private ChoiceBox<String> mcVerChoiceBox;

    @FXML
    private ChoiceBox<String> serverChoiceBox;
    private static boolean UpdatingVersions = false;
    private static List<String> mcVers = new ArrayList<>();

    public void initialize() {
        getMcVersLavel.setVisible(false);
        mainPane.setBackground(new Background(new BackgroundImage(new Image(String.valueOf(this.getClass().getResource("backgrounds/background_image.png")), 640, 360, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        serverChoiceBox.setItems(observableList(Server.getServers()));
        serverChoiceBox.setValue(Server.CraftBukkit.name());
    }

    @FXML
    public void onServerChange() {
        UpdatingVersions = true;
        getMcVersLavel.setVisible(true);
        mcVerChoiceBox.setDisable(true);
        Thread thread = new Thread(() -> {
            try {
                Class<?> clazz = Server.getClassByServer(Server.getServerByName(serverChoiceBox.getValue()));
                Method method = clazz.getMethod("getVersionList");
                Object instance = clazz.getConstructor().newInstance();
                mcVers = (List<String>) method.getReturnType().cast(method.invoke(instance));
                Platform.runLater(() -> {
                    mcVerChoiceBox.setItems(observableList(mcVers));
                    mcVerChoiceBox.setValue(mcVers.get(0));
                    mcVerChoiceBox.setDisable(false);
                });
                fadeOutLabel(getMcVersLavel);
            } catch (Exception e) {
                e.printStackTrace();
            }
            UpdatingVersions = false;
        });
        thread.start();
    }

    private void fadeOutLabel(Label label) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> {
            label.setVisible(false);
            label.setOpacity(1.0);
        });
        fadeTransition.play();
    }
}
