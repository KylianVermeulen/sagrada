package nl.avans.sagrada.view.popups;

import java.util.ArrayList;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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

public class Rondsnijder extends Popup {
    public static final int WIDTH_FLUXBORSTEL = 630;
    public static final int HEIGHT_FLUXBORSTEL = 170;
    private MyScene myScene;
    private HBox diePane;
    private HBox dieTrackPane;
    private VBox rootPane;
    private Game game;
    private Text textTop;
    private Text textBot;
    private ToolCard toolCard;
    private PlayerController playerController;
    private ArrayList<DieView> dieViews;
    private ArrayList<GameDie> gameDice;
    private ArrayList<GameDie> gameTrackDice;
    private GameDie die1;
    private GameDie die2;

    /**
     * Full constructor
     *
     * @param myScene MyScene
     * @param game Game
     * @param playerController PlayerController
     */
    public Rondsnijder(MyScene myScene, Game game, PlayerController playerController,
            ToolCard toolCard) {
        super(WIDTH_FLUXBORSTEL, HEIGHT_FLUXBORSTEL);
        this.myScene = myScene;
        this.game = game;
        this.playerController = playerController;
        this.toolCard = toolCard;

        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#fff"), new CornerRadii(26), null)));
        this.render();
    }

    @Override
    public void render() {
        dieViews = new ArrayList<>();
        gameDice = new ArrayList<>();
        gameTrackDice = new ArrayList<>();

        textTop = new Text();
        textBot = new Text();
        diePane = new HBox();
        diePane.setAlignment(Pos.CENTER);
        dieTrackPane = new HBox();
        dieTrackPane.setAlignment(Pos.BOTTOM_CENTER);
        rootPane = new VBox();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPrefSize(super.getwidth(), super.getheight());

        showRoundDice();
        showTrackDice();

        textTop.setText("Kies een dobbelsteen die je wil omruilen.");
        textBot.setText("Als je klikt heb je gekozen dus denk eerst na voordat je klikt!\n");
        textTop.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));
        textBot.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));
        rootPane.getChildren().addAll(textTop, textBot, diePane, dieTrackPane);
        this.getChildren().add(rootPane);
    }

    /**
     * Shows the dice of the current round in the popup.
     */
    private void showRoundDice() {
        int index = 0;
        for (GameDie gameDie : game.getRoundDice()) {
            gameDie.setInFirstTurn(playerController.getPlayer().isFirstTurn());
            int i = index;
            Pane paddingPane = new Pane();
            DieView dieView = new DieView(gameDie, playerController);
            dieViews.add(dieView);
            gameDice.add(gameDie);
            dieView.resize(25, 25);
            paddingPane.setPadding(new Insets(20));
            dieView.render();
            dieView.setOnMouseClicked(e -> {
                selectDice(i);
            });
            dieView.disableDrag();
            paddingPane.getChildren().add(dieView);
            diePane.getChildren().add(paddingPane);
            index++;
        }
    }

    /**
     * Shows the dice of the current round track in the game.
     */
    private void showTrackDice() {
        int index = 0;
        for (GameDie gameDie : game.getRoundTrackDice()) {
            gameDie.setInFirstTurn(playerController.getPlayer().isFirstTurn());
            int i = index;
            Pane paddingPane = new Pane();
            DieView dieView = new DieView(gameDie, playerController);
            dieViews.add(dieView);
            gameTrackDice.add(gameDie);
            dieView.resize(25, 25);
            paddingPane.setPadding(new Insets(20));
            dieView.render();
            dieView.setOnMouseClicked(e -> {
                selectTrackDice(i);
            });
            dieView.disableDrag();
            paddingPane.getChildren().add(dieView);
            dieTrackPane.getChildren().add(paddingPane);
            index++;
        }
    }

    /**
     * Select round dice, continue if track dice is selected.
     *
     * @param index int
     */
    private void selectDice(int index) {
        GameDie gameDie = gameDice.get(index);
        die1 = gameDie;
        if (die2 != null) {
            handle();
        }
    }

    /**
     * Select track dice, continue if round dice is selected.
     *
     * @param index int
     */
    private void selectTrackDice(int index) {
        GameDie gameDie = gameTrackDice.get(index);
        die2 = gameDie;
        if (die1 != null) {
            handle();
        }
    }

    /**
     * Handle the dice swap.
     */
    private void handle() {
        GameDieDao gameDieDao = new GameDieDao();
        final int die1Round = die1.getRound();
        final int die2Round = die2.getRound();
        die1.setIsOnOfferTable(false);
        die1.setOnRoundTrack(true);
        die1.setRound(die2Round);
        die2.setRound(die1Round);
        die2.setIsOnOfferTable(true);
        die2.setOnRoundTrack(false);
        die2.setInFirstTurn(playerController.getPlayer().isFirstTurn());
        gameDieDao.updateDie(game, die1);
        gameDieDao.updateDie(game, die2);
        showDie(die2);
    }

    /**
     * Shows the chosen die on the popup so you can place it.
     *
     * @param gameDie GameDie
     */
    private void showDie(GameDie gameDie) {
        gameDie.enablePopupdie();
        gameDie.setRound(playerController.getPlayer().getGame().getRound());
        DieView dieView = new DieView(gameDie, playerController);
        dieView.render();
        textTop.setText("Dit is de enigste dobbelsteen die je kunt plaatsen.");
        textBot.setText(
                "Sleep de steen naar de plek waar je hem wil plaatsen anders klik je op de knop.");
        diePane.getChildren().clear();
        rootPane.getChildren().clear();
        diePane.getChildren().add(dieView);
        Button button = new Button("Eindig beurt");
        button.setOnAction(e -> {
            myScene.removePopupPane();
            playerController.actionPass();
        });
        rootPane.getChildren().addAll(textTop, textBot, diePane, button);
    }
}
