package com.project.controller;

import com.project.model.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController {

    private List<String> genreList = new ArrayList<String>();
    private List<String> settingList = new ArrayList<String>();
    private List<String> companyList = new ArrayList<String>();
    private List<String> engineList = new ArrayList<String>();
    private List<String> platformList = new ArrayList<String>();

    private Stage primaryStage;

    private ViewController viewController;

    @FXML
    private TableView<Game> table;

    @FXML
    private TableColumn<String, Game> nameColumn;

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
    private ComboBox<String> platformBox;

    @FXML
    private CheckBox checkBox;

    @FXML
    private VBox vBox;

    @FXML
    private TextField searchField;

    ObservableList<Game> data = FXCollections.observableArrayList();

    public void addGame() throws IOException {
        Game game;
        String fxmlFile = "/fxml/view.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        viewController = loader.getController();
        viewController.setStage(this.primaryStage);
        primaryStage.setTitle("Курсач");
        primaryStage.setScene(new Scene(root));
        if(checkBox.isSelected()) {
            game = new Game(searchField.getText(),
                    genreBox.getSelectionModel().getSelectedItem(),
                    settingBox.getSelectionModel().getSelectedItem(),
                    companyDevelopBox.getSelectionModel().getSelectedItem(),
                    companyReleaseBox.getSelectionModel().getSelectedItem(),
                    engineBox.getSelectionModel().getSelectedItem(),
                    platformBox.getSelectionModel().getSelectedItem());
        } else {
            game = new Game();
            game.setName(searchField.getText());
        }
        viewController.setGame(game);
//        fill();
        primaryStage.show();
    }


    public void findGames() throws IOException {
        //здесь метод поиска подходящих игр
        Game game = new Game("Dota 2",
                "MOBA",
                "fantasy",
                "Valve",
                "Valve",
                "Source 2",
                "Windows");
        data.add(game);
        game = new Game("CS:GO",
                "shooter",
                "fantasy",
                "Valve",
                "Valve",
                "Source 2",
                "Windows");
        data.add(game);
        table.refresh();
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
        table.setItems(data);
        nameColumn.setCellValueFactory(new PropertyValueFactory<String, Game>("name"));
        table.refresh();
    }

    public void setLists(List<String> genreList,
                         List<String> settingList,
                         List<String> companyList,
                         List<String> engineList,
                         List<String> platformList) {
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
    }

    public void enableFilter(ActionEvent event) {
        vBox.setDisable(!checkBox.isSelected());
    }

    public void viewGame() throws IOException {
        String fxmlFile = "/fxml/view.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        viewController = loader.getController();
        viewController.setStage(this.primaryStage);
        primaryStage.setTitle("Курсач");
        primaryStage.setScene(new Scene(root));
        viewController.setGame(table.getSelectionModel().getSelectedItem());
        viewController.viewGame(table.getSelectionModel().getSelectedItem());
        primaryStage.show();
    }

    public void editGame() throws IOException {
        String fxmlFile = "/fxml/view.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        viewController = loader.getController();
        viewController.setStage(this.primaryStage);
        primaryStage.setTitle("Курсач");
        primaryStage.setScene(new Scene(root));
        viewController.setGame(table.getSelectionModel().getSelectedItem());
        viewController.editGame(table.getSelectionModel().getSelectedItem());
        primaryStage.show();
    }

    public void deleteGame() {

    }

}
