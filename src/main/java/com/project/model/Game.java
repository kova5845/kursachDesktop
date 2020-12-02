package com.project.model;

import javafx.beans.property.SimpleStringProperty;

public class Game {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty genre = new SimpleStringProperty();
    private SimpleStringProperty setting = new SimpleStringProperty();
    private SimpleStringProperty companyDevelop = new SimpleStringProperty();
    private SimpleStringProperty companyRelease = new SimpleStringProperty();
    private SimpleStringProperty engine = new SimpleStringProperty();
    private SimpleStringProperty platform = new SimpleStringProperty();

    public Game() {

    }

    public Game(String name,
                String genre,
                String setting,
                String companyDevelop,
                String companyRelease,
                String engine,
                String platform) {
        this.name.setValue(name);
        this.genre.setValue(genre);
        this.setting.setValue(setting);
        this.companyDevelop.setValue(companyDevelop);
        this.companyRelease.setValue(companyRelease);
        this.engine.setValue(engine);
        this.platform.setValue(platform);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getGenre() {
        return genre.get();
    }

    public SimpleStringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getSetting() {
        return setting.get();
    }

    public SimpleStringProperty settingProperty() {
        return setting;
    }

    public void setSetting(String setting) {
        this.setting.set(setting);
    }

    public String getCompanyDevelop() {
        return companyDevelop.get();
    }

    public SimpleStringProperty companyDevelopProperty() {
        return companyDevelop;
    }

    public void setCompanyDevelop(String companyDevelop) {
        this.companyDevelop.set(companyDevelop);
    }

    public String getCompanyRelease() {
        return companyRelease.get();
    }

    public SimpleStringProperty companyReleaseProperty() {
        return companyRelease;
    }

    public void setCompanyRelease(String companyRelease) {
        this.companyRelease.set(companyRelease);
    }

    public String getEngine() {
        return engine.get();
    }

    public SimpleStringProperty engineProperty() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine.set(engine);
    }

    public String getPlatform() {
        return platform.get();
    }

    public SimpleStringProperty platformProperty() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform.set(platform);
    }
}
