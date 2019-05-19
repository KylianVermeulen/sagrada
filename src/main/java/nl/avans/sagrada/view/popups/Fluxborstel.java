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

public class Fluxborstel extends Popup {
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
    private ArrayList<GameDie> gameDice;

    /**
     * Full constructor
     *
     * @param myScene MyScene
     * @param game Game
     * @param playerController PlayerController
     */
    public Fluxborstel(MyScene myScene, Game game, PlayerController playerController,
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
        dieViews = new ArrayList<DieView>();
        gameDice = new ArrayList<GameDie>();

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
                rerollDice(i);
            });
            paddingPane.getChildren().add(dieView);
            diePane.getChildren().add(paddingPane);
            index++;
        }
        textTop.setText("Kies een dobbelsteen die je opnieuw wil gooien.");
        textBot.setText("Als je klikt heb je gekozen dus denk eerst na voordat je klikt!\n");
        textTop.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));
        textBot.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));
        rootPane.getChildren().addAll(textTop, textBot, diePane);
        this.getChildren().add(rootPane);
    }

    /**
     * Rerolls the dice that is clicked.
     *
     * @param index int
     */
    private void rerollDice(int index) {
        GameDieDao gameDieDao = new GameDieDao();
        GameDie gameDie = gameDice.get(index);
        int newEyes = new Random().nextInt(6) + 1;
        gameDie.setEyes(newEyes);
        gameDieDao.updateGameDieEyes(game, gameDie);
        myScene.removePopupPane();
        playerController.actionPayForToolCard(toolCard);
        playerController.setActiveToolCard(null);
        playerController.viewGame();
    }
}
