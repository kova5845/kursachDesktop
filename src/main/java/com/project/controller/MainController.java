package com.project.controller;

import com.project.ostis.Game;
import com.project.ostis.GameRest;
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

    public ArrayList<String> gameNameList = new ArrayList<String>();

    private List<String> genreList = new ArrayList<String>();
    private List<String> settingList = new ArrayList<String>();
    private List<String> companyList = new ArrayList<String>();
    private List<String> engineList = new ArrayList<String>();
    private List<String> platformList = new ArrayList<String>();

    private Stage primaryStage;
    private Scene primaryScene;

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
        getViewForm();
        if(checkBox.isSelected()) {
            game = new Game(0,
                    searchField.getText(),
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
        viewController.addGame();
        primaryStage.show();
    }

    public void findGames() throws IOException {
        GameRest gameRest = new GameRest();
        data.clear();
        if (checkBox.isSelected()){
            if (searchField.getText() != null) {
                for (String name : gameNameList) {
                    System.out.println(name);
                    if (name.contains(searchField.getText())) {
                        if (gameRest.getGameWithFilter(name,
                                genreBox.getSelectionModel().getSelectedItem(),
                                settingBox.getSelectionModel().getSelectedItem(),
                                companyDevelopBox.getSelectionModel().getSelectedItem(),
                                companyReleaseBox.getSelectionModel().getSelectedItem(),
                                engineBox.getSelectionModel().getSelectedItem(),
                                platformBox.getSelectionModel().getSelectedItem())) {
                            Game game = new Game();
                            game.setName(name);
                            data.add(game);
                        }

                    }
                }
            }
                table.refresh();
        } else {
            if (searchField.getText() != null) {
                for (String name : gameNameList) {
                    System.out.println(name);
                    if (name.contains(searchField.getText())) {
                        Game game = new Game();
                        game.setName(name);
                        data.add(game);
                    }
                }
                table.refresh();
            }
        }
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

        genreBox.getSelectionModel().select("");
        settingBox.getSelectionModel().select("");
        companyDevelopBox.getSelectionModel().select("");
        companyReleaseBox.getSelectionModel().select("");
        engineBox.getSelectionModel().select("");
        platformBox.getSelectionModel().select("");

        this.gameNameList = gameNameList;
    }

    public void enableFilter(ActionEvent event) {
        vBox.setDisable(!checkBox.isSelected());
    }

    public void viewGame() throws IOException {
        getViewForm();
        viewController.setGame(table.getSelectionModel().getSelectedItem());
        viewController.viewGame(table.getSelectionModel().getSelectedItem());
        primaryStage.show();
    }

    public void editGame() throws IOException {
        getViewForm();
        viewController.setGame(table.getSelectionModel().getSelectedItem());
        viewController.editGame(table.getSelectionModel().getSelectedItem());
        primaryStage.show();
    }

    public void deleteGame() {
        GameRest gameRest = new GameRest();
        Game game = table.getSelectionModel().getSelectedItem();
        System.out.println(game.getName());
        System.out.println(gameRest.deleteGame(game));
        data.remove(game);
        gameNameList.remove(game.getName());
        table.refresh();
    }

    public void setPrimaryScene(Scene primaryScene) {
        this.primaryScene = primaryScene;
    }

    public Scene getPrimaryScene() {
        return this.primaryScene;
    }

    private void getViewForm() throws IOException {
        String fxmlFile = "/fxml/view.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        viewController = loader.getController();
        viewController.setStage(this.primaryStage);
        primaryStage.setTitle("Курсач");
        primaryStage.setScene(new Scene(root));
        viewController.setLists(genreList, settingList, companyList, engineList, platformList, gameNameList);
        viewController.setController(this);
    }

}
