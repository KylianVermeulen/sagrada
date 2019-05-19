package nl.avans.sagrada.view.popups;

import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.MyScene;

public class Fluxverwijderaar extends Popup {
    public static final int WIDTH_FLUXBORSTEL = 630;
    public static final int HEIGHT_FLUXBORSTEL = 340;

    private MyScene myScene;
    private HBox diePane;
    private VBox rootPane;
    private Game game;
    private Text textTop;
    private Text textBot;
    private ToolCard toolCard;
    private PlayerController playerController;
    private ArrayList<DieView> dieViews;
    private ArrayList<DieView> chooseDieViews;
    private ArrayList<GameDie> gameDice;

    /**
     * Full constructor
     *
     * @param myScene MyScene
     * @param game Game
     * @param playerController PlayerController
     */
    public Fluxverwijderaar(MyScene myScene, Game game, PlayerController playerController,
            ToolCard toolCard) {
        super(WIDTH_FLUXBORSTEL, HEIGHT_FLUXBORSTEL);
        this.myScene = myScene;
        this.game = game;
        this.toolCard = toolCard;
        this.playerController = playerController;

        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#fff"), new CornerRadii(26), null)));
        this.render();
    }

    @Override
    public void render() {
        dieViews = new ArrayList<DieView>();
        gameDice = new ArrayList<GameDie>();
        chooseDieViews = new ArrayList<DieView>();

        textTop = new Text();
        textBot = new Text();
        diePane = new HBox();
        diePane.setAlignment(Pos.CENTER);
        rootPane = new VBox();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPrefSize(super.getwidth(), super.getheight());
        int index = 0;
        for (GameDie gameDie : game.getRoundDice()) {
            int i = index;
            Pane paddingPane = new Pane();
            DieView dieView = new DieView(gameDie);
            dieViews.add(dieView);
            gameDice.add(gameDie);
            dieView.resize(25, 25);
            paddingPane.setPadding(new Insets(20));
            dieView.render();
            dieView.setOnMouseClicked(e -> {
                showChooseDie(i);
            });
            paddingPane.getChildren().add(dieView);
            diePane.getChildren().add(paddingPane);
            index++;
        }
        textTop.setText("Kies een dobbelsteen waarvan je de ogen wil bepalen.");
        textBot.setText("Als je klikt heb je gekozen dus denk eerst na voordat je klikt!\n");
        textTop.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));
        textBot.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));
        rootPane.getChildren().addAll(textTop, textBot, diePane);
        this.getChildren().add(rootPane);
    }

    /**
     * Chose the die that you want to change the eyes from
     *
     * @param index int
     */
    private void showChooseDie(int index) {
        rootPane.getChildren().clear();
        diePane.getChildren().clear();
        GameDie gameDie = gameDice.get(index);
        String dieColor = gameDie.getColor();
        int dieNumber = gameDie.getNumber();
        for (int i = 0; i < 6; i++) {
            GameDie die = new GameDie(dieNumber, dieColor, i + 1);
            Pane paddingPane = new Pane();
            DieView dieView = new DieView(die);
            chooseDieViews.add(dieView);
            dieView.resize(25, 25);
            paddingPane.setPadding(new Insets(20));
            dieView.render();
            dieView.setOnMouseClicked(e -> {
                chooseDie(die);
            });
            paddingPane.getChildren().add(dieView);
            diePane.getChildren().add(paddingPane);
        }

        textTop.setText("Kies de dobbelsteen die je wil gebruiken.");
        textBot.setText("Als je klikt heb je gekozen dus denk eerst na voordat je klikt!\n");
        rootPane.getChildren().addAll(textTop, textBot, diePane);
    }

    /**
     * Changes the eyes of the die in the database
     *
     * @param gameDie GameDie
     */
    private void chooseDie(GameDie gameDie) {
        GameDieDao gameDieDao = new GameDieDao();
        gameDieDao.updateGameDieEyes(game, gameDie);
        playerController.actionPayForToolCard(toolCard);
        myScene.removePopupPane();
        playerController.viewGame();
    }
}
