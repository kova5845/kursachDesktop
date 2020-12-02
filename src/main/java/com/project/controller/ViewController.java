package com.project.controller;

import com.project.model.Game;
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
        Game game = new Game(nameField.getText(),
                genreBox.getSelectionModel().getSelectedItem(),
                settingBox.getSelectionModel().getSelectedItem(),
                companyDevelopBox.getSelectionModel().getSelectedItem(),
                companyReleaseBox.getSelectionModel().getSelectedItem(),
                engineBox.getSelectionModel().getSelectedItem(),
                platformBox.getSelectionModel().getSelectedItem());
        // здесь происходит добавление в остис
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
        getInfo();
        mainController.setLists(genreList, settingList, companyList, engineList, platformList);
        primaryStage.show();
    }

    private void getInfo() {
        genreList.addAll(Arrays.asList("MOBA", "shooter"));
        settingList.addAll(Arrays.asList("fantasy", "XX century"));
        companyList.addAll(Arrays.asList("Valve", "Nival"));
        engineList.addAll(Arrays.asList("Source 2", "Unity"));
        platformList.addAll(Arrays.asList("Windows", "Linux"));
        // здесь должны быть методы взятия из остиса
    }

    public void viewGame(Game game) {
        getInfo();
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

    public void editGame(Game game) {
        getInfo();
        genreBox.setItems(FXCollections.observableArrayList(genreList));
        settingBox.setItems(FXCollections.observableArrayList(settingList));
        companyDevelopBox.setItems(FXCollections.observableArrayList(companyList));
        companyReleaseBox.setItems(FXCollections.observableArrayList(companyList));
        engineBox.setItems(FXCollections.observableArrayList(engineList));
        platformBox.setItems(FXCollections.observableArrayList(platformList));

        nameField.setText(game.getName());
        genreBox.getSelectionModel().select(game.getGenre());
        settingBox.getSelectionModel().select(game.getSetting());
        companyDevelopBox.getSelectionModel().select(game.getCompanyDevelop());
        companyReleaseBox.getSelectionModel().select(game.getCompanyRelease());
        engineBox.getSelectionModel().select(game.getEngine());
        platformBox.getSelectionModel().select(game.getPlatform());
    }

}
