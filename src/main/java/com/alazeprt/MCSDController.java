package com.alazeprt;

import com.alazeprt.http.servers.utils.GetBukkitHandler;
import com.alazeprt.servers.Server;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

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
    private ChoiceBox<String> mcVerChoiceBox;

    @FXML
    private ChoiceBox<String> serverChoiceBox;

    public void initialize() {
        mainPane.setBackground(new Background(new BackgroundImage(new Image(String.valueOf(this.getClass().getResource("backgrounds/background_image.png")), 640, 360, false, false), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        serverChoiceBox.setItems(observableList(Server.getServers()));
        serverChoiceBox.setValue(Server.Spigot.name());
        mcVerChoiceBox.setItems(observableList(GetBukkitHandler.getVersions(serverChoiceBox.getValue())));
        mcVerChoiceBox.setValue(GetBukkitHandler.getVersions(serverChoiceBox.getValue()).get(0));
    }
}
