package com.alazeprt;

import com.alazeprt.http.utils.MultiThreadDownloader;
import com.alazeprt.servers.Server;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.alazeprt.http.utils.MultiThreadDownloader.maxProgress;
import static com.alazeprt.http.utils.MultiThreadDownloader.progress;
import static javafx.collections.FXCollections.observableList;

public class MCSDController {
    @FXML
    public TextArea backgroundInfo;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private Button DownloadButton;

    @FXML
    private ChoiceBox<String> mcVerChoiceBox;

    @FXML
    private ChoiceBox<String> serverChoiceBox;
    private static List<String> mcVers = new ArrayList<>();

    public void initialize() {
        serverChoiceBox.setItems(observableList(Server.getServers()));
        serverChoiceBox.setValue(Server.CraftBukkit.name());
        backgroundInfo.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> {
            backgroundInfo.setScrollTop(Double.MAX_VALUE); //this will scroll to the bottom
            //use Double.MIN_VALUE to scroll to the top
        });
    }

    @FXML
    public void onServerChange() {
        backgroundInfo.appendText("正在获取 " + serverChoiceBox.getValue() + " 服务端的版本信息...\n");
        mcVerChoiceBox.setDisable(true);
        serverChoiceBox.setDisable(true);
        DownloadButton.setDisable(true);
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
                    serverChoiceBox.setDisable(false);
                    DownloadButton.setDisable(false);
                    backgroundInfo.appendText("成功获取 " + serverChoiceBox.getValue() + " 服务端的版本信息!\n");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    @FXML
    public void onDownloadServer() {
        backgroundInfo.appendText("准备开始下载 " + serverChoiceBox.getValue() + " 服务端 " + mcVerChoiceBox.getValue() + " 版本...\n");
        DownloadButton.setDisable(true);
        mcVerChoiceBox.setDisable(true);
        serverChoiceBox.setDisable(true);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择保存服务端的位置");
        File file = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
        if (file == null) {
            backgroundInfo.appendText("未选择文件路径!\n");
            DownloadButton.setDisable(false);
            mcVerChoiceBox.setDisable(false);
            serverChoiceBox.setDisable(false);
            return;
        }
        Thread thread = new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    backgroundInfo.appendText("正在获取 " + serverChoiceBox.getValue() + " 服务端 " + mcVerChoiceBox.getValue() + " 版本的下载链接...\n");
                });
                Class<?> clazz = Server.getClassByServer(Server.getServerByName(serverChoiceBox.getValue()));
                Method method = clazz.getMethod("getServerUrl", String.class);
                Object instance = clazz.getConstructor().newInstance();
                String url = (String) method.getReturnType().cast(method.invoke(instance, mcVerChoiceBox.getValue()));
                Platform.runLater(() -> backgroundInfo.appendText("开始下载 " + serverChoiceBox.getValue() + " 服务端 " + mcVerChoiceBox.getValue() + " 版本...\n"));
                Thread thread1 = new Thread(() -> {
                    while(progress < maxProgress) {
                        Platform.runLater(() -> backgroundInfo.appendText("下载进度: " + progress + " / " + maxProgress + "\n"));
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                MultiThreadDownloader.downloadFile(url, file.getAbsolutePath(), 16, thread1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                backgroundInfo.appendText("成功下载 " + serverChoiceBox.getValue() + " 服务端 " + mcVerChoiceBox.getValue() + " 版本!\n");
                DownloadButton.setDisable(false);
                mcVerChoiceBox.setDisable(false);
                serverChoiceBox.setDisable(false);
            });
            maxProgress = -1;
            progress = -1;
        });
        thread.start();
    }
}
