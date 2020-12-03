package com.project.ostis;

import java.util.ArrayList;

public class GameRest {
    public ScAddr COMPUTER_GAME;
    public ScAddr GENRE;
    public ScAddr SETTING;
    public ScAddr COMPANY_DEVELOP;
    public ScAddr COMPANY_RELEASE;
    public ScAddr PLATFORM;
    public ScAddr ENGINE;
    public ScAddr ID;
    public ScAddr COMPANY;
    public ScAddr ENGINE_CONCEPT;
    public ScAddr PLATFORM_CONCEPT;

    private SctpClient sctpClient;

    public ArrayList<String> getAttr(ScAddr attribute) {
        this.connect();
        ArrayList<String> attrList = new ArrayList<String>();
        SctpIterator iter = sctpClient.iterate3(SctpIterator.Iterator3F_A_A,
                attribute,
                new ScType(ScType.ArcPosConstPerm),
                new ScType(ScType.Node));
        while(iter.next()){
            String attr = get(iter.value(2), ID);
            if (!"".equals(attr)) {
                attrList.add(get(iter.value(2), ID));
            }
        }
        return attrList;
    }

    private String get(ScAddr game, ScAddr elem){
        SctpIterator iter5 = sctpClient.iterate5(SctpIterator.Iterator5F_A_A_A_F,
                game,
                new ScType(),
                new ScType(),
                new ScType(),
                elem);
        if(iter5.next()){
            if(sctpClient.getLinkContent(iter5.value(2)) != null){
                return SctpClient.ByteBufferToString(sctpClient.getLinkContent(iter5.value(2)));
            }
            else{
                return this.get(iter5.value(2), ID);
            }
        }
        return "";
    }

    private String get3(ScAddr game, ScAddr elem){
        SctpIterator iter3 = sctpClient.iterate3(SctpIterator.Iterator3F_A_A,
                                                elem,
                                                new ScType(),
                                                new ScType());
        while(iter3.next()){
            SctpIterator iter32 = sctpClient.iterate3(SctpIterator.Iterator3F_A_F,
                                                        iter3.value(2),
                                                        new ScType(),
                                                        game);
            if(iter32.next()){
                return this.get(iter3.value(2), ID);
            }
        }
        return "";
    }

    public Game getGameID(String name){
        Game game = new Game();
        game.setName(name);
        ScAddr[] nameAddr = sctpClient.findLinksByContent(SctpClient.ByteBufferFromString(name));
        if(nameAddr == null)
            return null;
        SctpIterator iter5 = sctpClient.iterate5(SctpIterator.Iterator5A_A_F_A_F,
                new ScType(),
                new ScType(),
                nameAddr[0],
                new ScType(),
                ID);

        game.setScAddr(iter5.value(0).getValue());
        game.setName(this.get(iter5.value(0), ID));
        game.setCompanyDevelop(this.get(iter5.value(0), COMPANY_DEVELOP));
        game.setCompanyRelease(this.get(iter5.value(0), COMPANY_RELEASE));
        game.setEngine(this.get(iter5.value(0), ENGINE));
        game.setPlatform(this.get(iter5.value(0), PLATFORM));
        game.setGenre(this.get3(iter5.value(0), GENRE));
        game.setSetting(this.get3(iter5.value(0), SETTING));
        return game;
    }

    public ArrayList<String> getGames() {
        this.connect();
        ArrayList<ScAddr> arrOfGamesAddr = new ArrayList<ScAddr>();
        ArrayList<String> games = new ArrayList<String>();
        SctpIterator iter = sctpClient.iterate3(SctpIterator.Iterator3F_A_A,
                                                COMPUTER_GAME,
                                                new ScType(ScType.ArcPosConstPerm),
                                                new ScType(ScType.Node));
        while(iter.next()){
            String game = this.get(iter.value(2), ID);
            if (!"".equals(game)) {
                games.add(this.get(iter.value(2), ID));
            }
        }
        return games;
    }

    private ScAddr findNodeById(ScAddr addr, String name) {
        SctpIterator iter3 = sctpClient.iterate3(SctpIterator.Iterator3F_A_A,
                                                addr,
                                                new ScType(),
                                                new ScType());
        if(iter3 == null){
            return null;
        }
        while (iter3.next()) {
            if(name.equals(this.get(iter3.value(2), ID))){
                return iter3.value(2);
            }
        }
        return null;
    }

    public Game getGame(String name){
        this.connect();
        System.out.println("name of game is:" + name);
        Game game = new Game();
        game.setName(name);
        ScAddr scGame = this.findNodeById(COMPUTER_GAME, name);
        if(scGame == null)
            return null;
        game.setScAddr(scGame.getValue());
        game.setName(this.get(scGame, ID));
        game.setCompanyDevelop(this.get(scGame, COMPANY_DEVELOP));
        game.setCompanyRelease(this.get(scGame, COMPANY_RELEASE));
        game.setEngine(this.get(scGame, ENGINE));
        game.setPlatform(this.get(scGame, PLATFORM));
        game.setGenre(this.get3(scGame, GENRE));
        game.setSetting(this.get3(scGame, SETTING));
        return game;
    }

    private void addToName(ScAddr game, String nodeName){
        ScAddr name = sctpClient.createLink();
        sctpClient.setLinkContent(name, SctpClient.ByteBufferFromString(nodeName));
        ScAddr nameArc = sctpClient.createArc(game, name, new ScType(ScType.ArcCommon));
        sctpClient.createArc(ID, nameArc, new ScType(ScType.ArcPosConstPerm));
    }

    private void addToGame(ScAddr game, ScAddr node, ScAddr nrel){
        if(node == null) {
            return;
        }
        ScAddr nameArc = sctpClient.createArc(game, node, new ScType(ScType.ArcCommon));
        sctpClient.createArc(nrel, nameArc, new ScType(ScType.ArcPosConstPerm));
    }

    public ScAddr setGame(Game game) {
        this.connect();
        System.out.println(game.getScAddr() +
                game.getName() +
                game.getCompanyDevelop() +
                game.getCompanyRelease() +
                game.getEngine() +
                game.getPlatform() +
                game.getGenre() +
                game.getSetting());
        ScAddr conEngine = sctpClient.findElementBySystemIdentifier("game_engine");
        ScAddr conPlatform = sctpClient.findElementBySystemIdentifier("game_platform");
        ScAddr conCompany = sctpClient.findElementBySystemIdentifier("concept_company");
        ScAddr scGame = sctpClient.createNode(new ScType(ScType.Node));
        if(scGame == null){
            return null;
        }
        sctpClient.setSystemIdentifier(scGame, "computer_game_" +
                game.getName().replace(" ", "_"));
        this.addToName(scGame, game.getName());
        ScAddr genre = this.findNodeById(GENRE, game.getGenre());
        if(genre != null)
            sctpClient.createArc(genre, scGame, new ScType(ScType.ArcPosConstPerm));
        ScAddr setting = this.findNodeById(SETTING, game.getSetting());
        if(setting != null)
            sctpClient.createArc(setting, scGame, new ScType(ScType.ArcPosConstPerm));
        ScAddr engine = this.findNodeById(conEngine, game.getEngine());
        if(engine != null)
            this.addToGame(scGame, engine, ENGINE);
        ScAddr companyDevelop = this.findNodeById(conCompany, game.getCompanyDevelop());
        if(companyDevelop != null)
            this.addToGame(scGame, companyDevelop, COMPANY_DEVELOP);
        ScAddr companyRelease = this.findNodeById(conCompany, game.getCompanyRelease());
        if(companyRelease != null)
            this.addToGame(scGame, companyRelease, COMPANY_RELEASE);
        ScAddr platform = this.findNodeById(conPlatform, game.getPlatform());
        if(platform != null)
            this.addToGame(scGame, platform, PLATFORM);
        sctpClient.createArc(COMPUTER_GAME, scGame, new ScType(ScType.ArcPosConstPerm));
        return scGame;
    }

    private void deleteArc(ScAddr game, ScAddr node){
        SctpIterator iter = sctpClient.iterate5(SctpIterator.Iterator5F_A_A_A_F,
                                                game,
                                                new ScType(),
                                                new ScType(),
                                                new ScType(),
                                                node);
        if(iter.next()){
            System.out.println(sctpClient.eraseElement(iter.value(1)));
        }
    }

    private void deleteArc3(ScAddr game, ScAddr node, ScAddr elem){
        SctpIterator iter = sctpClient.iterate3(SctpIterator.Iterator3F_A_F,
                                                elem,
                                                new ScType(),
                                                node);
        while(iter.next()){
            SctpIterator iter3 = sctpClient.iterate3(SctpIterator.Iterator3F_A_F,
                                                    node,
                                                    new ScType(),
                                                    game);
            if(iter3.next()){
                sctpClient.eraseElement(iter3.value(1));
                return;
            }
        }

        if(iter.next()){
            System.out.println(sctpClient.eraseElement(iter.value(1)));
        }
    }

    public ScAddr updateGame(Game game){
        this.connect();
        System.out.println(game.getScAddr() +
                game.getName() +
                game.getCompanyDevelop() +
                game.getCompanyRelease() +
                game.getEngine() +
                game.getPlatform() +
                game.getGenre() +
                game.getSetting());
        ScAddr conEngine = sctpClient.findElementBySystemIdentifier("game_engine");
        ScAddr conPlatform = sctpClient.findElementBySystemIdentifier("game_platform");
        ScAddr conCompany = sctpClient.findElementBySystemIdentifier("concept_company");
        ScAddr scGame = this.findNodeById(COMPUTER_GAME, game.getName());
        if(scGame == null){
            return null;
        }
        this.deleteArc(scGame, ID);
        this.addToName(scGame, game.getName());
        ScAddr genre = this.findNodeById(GENRE, game.getGenre());
        if(genre != null) {
            this.deleteArc3(scGame, genre, GENRE);
            sctpClient.createArc(genre, scGame, new ScType(ScType.ArcPosConstPerm));
        }
        ScAddr setting = this.findNodeById(SETTING, game.getSetting());
        if(setting != null){
            this.deleteArc3(scGame, setting, SETTING);
            sctpClient.createArc(setting, scGame, new ScType(ScType.ArcPosConstPerm));
        }
        ScAddr engine = this.findNodeById(conEngine, game.getEngine());
        if(engine != null) {
            this.deleteArc(scGame, ENGINE);
            this.addToGame(scGame, engine, ENGINE);
        }
        ScAddr companyDevelop = this.findNodeById(conCompany, game.getCompanyDevelop());
        if(companyDevelop != null) {
            this.deleteArc(scGame, COMPANY_DEVELOP);
            this.addToGame(scGame, companyDevelop, COMPANY_DEVELOP);
        }
        ScAddr companyRelease = this.findNodeById(conCompany, game.getCompanyRelease());
        if(companyRelease != null) {
            this.deleteArc(scGame, COMPANY_RELEASE);
            this.addToGame(scGame, companyRelease, COMPANY_RELEASE);
        }
        ScAddr platform = this.findNodeById(conPlatform, game.getPlatform());
        if(platform != null) {
            this.deleteArc(scGame, PLATFORM);
            this.addToGame(scGame, platform, PLATFORM);
        }
        return scGame;
    }

    public boolean deleteGame(String name){
        this.connect();
        ScAddr scGame = this.findNodeById(COMPUTER_GAME, name);
        if(scGame == null){
            return false;
        }
        return sctpClient.eraseElement(scGame);
    }

    public boolean connect() {
        boolean flag = false;
        sctpClient = new SctpClient();
        if(sctpClient.connect("localhost", 55770)) {
            System.out.println("Connect success");
            flag = true;
        }
        COMPUTER_GAME = sctpClient.findElementBySystemIdentifier("concept_computer_game");
        GENRE = sctpClient.findElementBySystemIdentifier("concept_game_genre");
        SETTING = sctpClient.findElementBySystemIdentifier("concept_setting");
        COMPANY_DEVELOP = sctpClient.findElementBySystemIdentifier("nrel_developer");
        COMPANY_RELEASE = sctpClient.findElementBySystemIdentifier("nrel_publisher");
        PLATFORM = sctpClient.findElementBySystemIdentifier("nrel_platform");
        ENGINE = sctpClient.findElementBySystemIdentifier("nrel_game_engine");
        ID = sctpClient.findElementBySystemIdentifier("nrel_main_idtf");
        COMPANY = sctpClient.findElementBySystemIdentifier("concept_company");
        ENGINE_CONCEPT = sctpClient.findElementBySystemIdentifier("game_engine");
        PLATFORM_CONCEPT = sctpClient.findElementBySystemIdentifier("game_platform");

//        this.getGames();
//        Game game = this.getGame("test_game_5");
//        System.out.println(game.getScAddr() +
//                game.getName() +
//                game.getCompanyDevelop() +
//                game.getCompanyRelease() +
//                game.getEngine() +
//                game.getPlatform() +
//                game.getGenre() +
//                game.getSetting());
//        Game game = new Game(0,
//                "test_game3",
//                "МОБА",
//                "fantasy",
//                "Valve",
//                "Valve",
//                "Source 2",
//                "Windows");
//        System.out.println(this.setGame(game).getValue());
        return flag;
    }

    public static void main(String[] args) {
        GameRest gameRest = new GameRest();
        gameRest.connect();
    }
}
