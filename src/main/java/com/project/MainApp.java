package com.project;

import com.project.controller.MainController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApp extends Application {

    private List<String> genreList = new ArrayList<String>();
    private List<String> settingList = new ArrayList<String>();
    private List<String> companyList = new ArrayList<String>();
    private List<String> engineList = new ArrayList<String>();
    private List<String> platformList = new ArrayList<String>();

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        String fxmlFile = "/fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        MainController mainController = loader.getController();
        mainController.setStage(stage);
        stage.setTitle("Курсач");
        stage.setScene(new Scene(root));
        getInfo();
        mainController.setLists(genreList, settingList, companyList, engineList, platformList);
        stage.show();
    }

    private void getInfo() {
        genreList.addAll(Arrays.asList("MOBA", "shooter"));
        settingList.addAll(Arrays.asList("fantasy", "XX century"));
        companyList.addAll(Arrays.asList("Valve", "Nival"));
        engineList.addAll(Arrays.asList("Source 2", "Unity"));
        platformList.addAll(Arrays.asList("Windows", "Linux"));
        // здесь должны быть методы взятия из остиса
    }
}
