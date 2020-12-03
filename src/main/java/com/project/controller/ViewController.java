package com.project.controller;

import com.project.ostis.Game;
import com.project.ostis.GameRest;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewController {

    private String mode;
    private Game globalGame;

    private ArrayList<String> gameNameList = new ArrayList<String>();

    private List<String> genreList = new ArrayList<String>();
    private List<String> settingList = new ArrayList<String>();
    private List<String> companyList = new ArrayList<String>();
    private List<String> engineList = new ArrayList<String>();
    private List<String> platformList = new ArrayList<String>();

    private Stage primaryStage;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> genreBox;

    @FXML
    private ComboBox<String> settingBox;

    @FXML
    private ComboBox<String> companyDevelopBox;

    @FXML
    private ComboBox<String> companyReleaseBox;

    @FXML
    private ComboBox<String> engineBox;

    @FXML
    private ComboBox<String>platformBox;

    @FXML
    private Button addButton;


    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setGame(Game game) {
    }

    public void add() throws IOException {
        GameRest gameRest = new GameRest();
        if ("insert".equals(mode)) {
            Game game = new Game(0,
                    nameField.getText(),
                    genreBox.getSelectionModel().getSelectedItem(),
                    settingBox.getSelectionModel().getSelectedItem(),
                    companyDevelopBox.getSelectionModel().getSelectedItem(),
                    companyReleaseBox.getSelectionModel().getSelectedItem(),
                    engineBox.getSelectionModel().getSelectedItem(),
                    platformBox.getSelectionModel().getSelectedItem());
            gameRest.setGame(game);

        }
        if ("update".equals(mode)) {
            globalGame.setName(nameField.getText());
            globalGame.setGenre(genreBox.getSelectionModel().getSelectedItem());
            globalGame.setSetting(settingBox.getSelectionModel().getSelectedItem());
            globalGame.setCompanyDevelop(companyDevelopBox.getSelectionModel().getSelectedItem());
            globalGame.setCompanyRelease(companyReleaseBox.getSelectionModel().getSelectedItem());
            globalGame.setEngine(engineBox.getSelectionModel().getSelectedItem());
            globalGame.setPlatform(platformBox.getSelectionModel().getSelectedItem());
            gameRest.updateGame(globalGame);
        }
        viewMain();
    }

    public void cancel() throws IOException {
        viewMain();
    }

    private void viewMain() throws IOException {
        String fxmlFile = "/fxml/main.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        MainController mainController = loader.getController();
        mainController.setStage(primaryStage);
        primaryStage.setTitle("Курсач");
        primaryStage.setScene(new Scene(root));
//        mainController.setLists(genreList, settingList, companyList, engineList, platformList);
        primaryStage.show();
    }

    public void viewGame(Game game) {
        genreBox.setItems(FXCollections.observableArrayList(genreList));
        settingBox.setItems(FXCollections.observableArrayList(settingList));
        companyDevelopBox.setItems(FXCollections.observableArrayList(companyList));
        companyReleaseBox.setItems(FXCollections.observableArrayList(companyList));
        engineBox.setItems(FXCollections.observableArrayList(engineList));
        platformBox.setItems(FXCollections.observableArrayList(platformList));

        nameField.setDisable(true);
        genreBox.setDisable(true);
        settingBox.setDisable(true);
        companyDevelopBox.setDisable(true);
        companyReleaseBox.setDisable(true);
        engineBox.setDisable(true);
        platformBox.setDisable(true);

        nameField.setText(game.getName());
        genreBox.getSelectionModel().select(game.getGenre());
        settingBox.getSelectionModel().select(game.getSetting());
        companyDevelopBox.getSelectionModel().select(game.getCompanyDevelop());
        companyReleaseBox.getSelectionModel().select(game.getCompanyRelease());
        engineBox.getSelectionModel().select(game.getEngine());
        platformBox.getSelectionModel().select(game.getPlatform());

        addButton.setVisible(true);
    }

    public void editGame(Game gameName) {
        mode = "update";
        GameRest gameRest = new GameRest();
        genreBox.setItems(FXCollections.observableArrayList(genreList));
        settingBox.setItems(FXCollections.observableArrayList(settingList));
        companyDevelopBox.setItems(FXCollections.observableArrayList(companyList));
        companyReleaseBox.setItems(FXCollections.observableArrayList(companyList));
        engineBox.setItems(FXCollections.observableArrayList(engineList));
        platformBox.setItems(FXCollections.observableArrayList(platformList));

        globalGame = gameRest.getGame(gameName.getName());

        nameField.setText(globalGame.getName());
        genreBox.getSelectionModel().select(globalGame.getGenre());
        settingBox.getSelectionModel().select(globalGame.getSetting());
        companyDevelopBox.getSelectionModel().select(globalGame.getCompanyDevelop());
        companyReleaseBox.getSelectionModel().select(globalGame.getCompanyRelease());
        engineBox.getSelectionModel().select(globalGame.getEngine());
        platformBox.getSelectionModel().select(globalGame.getPlatform());

        addButton.setText("Редактировать");

    }

    public void setLists(List<String> genreList,
                         List<String> settingList,
                         List<String> companyList,
                         List<String> engineList,
                         List<String> platformList,
                         ArrayList<String> gameNameList) {
        this.genreList = genreList;
        this.settingList = settingList;
        this.companyList = companyList;
        this.engineList = engineList;
        this.platformList = platformList;
        genreBox.setItems(FXCollections.observableArrayList(genreList));
        settingBox.setItems(FXCollections.observableArrayList(settingList));
        companyDevelopBox.setItems(FXCollections.observableArrayList(companyList));
        companyReleaseBox.setItems(FXCollections.observableArrayList(companyList));
        engineBox.setItems(FXCollections.observableArrayList(engineList));
        platformBox.setItems(FXCollections.observableArrayList(platformList));

        this.gameNameList = gameNameList;
    }

    public void addGame() {
        mode = "insert";
        genreBox.getSelectionModel().select("");
        settingBox.getSelectionModel().select("");
        companyDevelopBox.getSelectionModel().select("");
        companyReleaseBox.getSelectionModel().select("");
        engineBox.getSelectionModel().select("");
        platformBox.getSelectionModel().select("");
    }


}
