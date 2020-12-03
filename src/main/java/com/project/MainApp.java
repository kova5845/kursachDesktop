package com.project;

import com.project.controller.MainController;
import com.project.ostis.Game;
import com.project.ostis.GameRest;
import com.project.ostis.ScAddr;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApp extends Application {

    GameRest gameRest;

    private ArrayList<String> gameNameList = new ArrayList<String>();

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
        mainController.setLists(genreList, settingList, companyList, engineList, platformList, gameNameList);
        stage.show();
    }

    private void getInfo() {
        gameRest = new GameRest();
        gameRest.connect();
        genreList.add("");
        settingList.add("");
        companyList.add("");
        engineList.add("");
        platformList.add("");

        genreList.addAll(FXCollections.observableArrayList(gameRest.getAttr(gameRest.GENRE)));
        settingList.addAll(FXCollections.observableArrayList(gameRest.getAttr(gameRest.SETTING)));
        companyList.addAll(FXCollections.observableArrayList(gameRest.getAttr(gameRest.COMPANY)));
        engineList.addAll(FXCollections.observableArrayList(gameRest.getAttr(gameRest.ENGINE_CONCEPT)));
        platformList.addAll(FXCollections.observableArrayList(gameRest.getAttr(gameRest.PLATFORM_CONCEPT)));
        gameNameList.addAll(gameRest.getGames());

//        ArrayList<Game> games = gameRest.getGames();
//        for (Game game : games) {
//            System.out.println(game.getName() + " " + game.getGenre());
//        }
//        Game game = gameRest.getGame("Dota 2");
//        System.out.println(game.getName());
//        System.out.println(game.getGenre());
    }
}
