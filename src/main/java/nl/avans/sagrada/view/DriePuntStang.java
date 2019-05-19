package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.view.popups.Alert;
import nl.avans.sagrada.view.popups.AlertType;
import nl.avans.sagrada.view.popups.Popup;
import java.util.ArrayList;

public class DriePuntStang extends Popup {
    private final int BORDER_PANE_HEIGHT = 800;
    private final int BORDER_PANE_WIDTH = 1280;
    private final int BUTTON_PANE_HEIGHT = 70;
    private final int BUTTON_PANE_WIDTH = 1280;
    private final int BUTTON_HEIGHT = 25;
    private final int BUTTON_WIDTH = 50;
    private boolean dieSelected;
    private MyScene myScene;
    private ArrayList<DieView> dieViews;
    private ArrayList<GameDie> gameDice;
    private Game game;
    private PlayerController playerController;
    private ToolCard activeToolCard;
    private MouseEvent event;
    private HBox diePane;
    private VBox rootPane;
    private Button plusButton;
    private Button minButton;
    private Button backButton;
    private BorderPane buttonPane;

    public DriePuntStang(MyScene myScene, PlayerController playerController, Game game, ToolCard activeToolCard) {
        super(0, 0, 1280, 800);
        dieSelected = false;
        this.myScene = myScene;
        this.playerController = playerController;
        this.game = game;
        this.activeToolCard = activeToolCard;

        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.render();
    }

    @Override
    public void render() {
        buildButtons();
        buildButtonPane();
        buildDieView();
        buildBorderPane();
    }

    /**
     * Method to build the buttons
     */
    private void buildButtons() {
        plusButton = new Button("+");
        plusButton.setMaxHeight(BUTTON_HEIGHT);
        plusButton.setMinHeight(BUTTON_HEIGHT);
        plusButton.setMaxWidth(BUTTON_WIDTH);
        plusButton.setMinWidth(BUTTON_WIDTH);
        plusButton.setOnAction(e -> {
            increaseEyes();
        });
        minButton = new Button("-");
        minButton.setMaxHeight(BUTTON_HEIGHT);
        minButton.setMinHeight(BUTTON_HEIGHT);
        minButton.setMaxWidth(BUTTON_WIDTH);
        minButton.setMinWidth(BUTTON_WIDTH);
        minButton.setOnAction(e -> {
            decreaseEyes();
        });
    }

    /**
     * method to build the ButtonPane
     */
    private void buildButtonPane() {
        buttonPane = new BorderPane();
        buttonPane.setMaxHeight(BUTTON_PANE_HEIGHT);
        buttonPane.setMinHeight(BUTTON_PANE_HEIGHT);
        buttonPane.setMaxWidth(BUTTON_PANE_WIDTH);
        buttonPane.setMinWidth(BUTTON_PANE_WIDTH);

        HBox centerButtonsPane = new HBox();
        centerButtonsPane.getChildren().addAll(plusButton, minButton);
        buttonPane.setLeft(backButton);
        buttonPane.setCenter(centerButtonsPane);
    }

    /**
     * Method to build the BorderPane
     */
    private void buildBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setMaxHeight(BORDER_PANE_HEIGHT);
        borderPane.setMinHeight(BORDER_PANE_HEIGHT);
        borderPane.setMaxWidth(BORDER_PANE_WIDTH);
        borderPane.setMinWidth(BORDER_PANE_WIDTH);

        borderPane.setCenter(diePane);
        borderPane.setBottom(buttonPane);
        getChildren().add(borderPane);
    }

    private void buildDieView() {
        dieViews = new ArrayList<>();
        gameDice = new ArrayList<>();

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
            dieView.setOnMouseClicked(e -> {
                dieView.setSelected(true);
            });
            dieView.render();
            paddingPane.getChildren().add(dieView);
            diePane.getChildren().add(paddingPane);
            index++;
        }
        rootPane.getChildren().add(diePane);
        getChildren().add(rootPane);
    }

    public void increaseEyes() {
        GameDieDao gameDieDao = new GameDieDao();
        if (activeToolCard != null) {
            for (int index = 0; index < dieViews.size(); index++) {
                GameDie gameDie = dieViews.get(index).getGameDie();
                //increase die eye value
                if (dieViews.get(index).isSelected() == true) {
                    if (gameDie.getEyes() != 6) {
                        activeToolCard = null;
                        gameDie.setEyes(gameDie.getEyes() + 1);
                        gameDieDao.updateDie(game, gameDie);
                        myScene.removePopupPane();
                        playerController.viewGame();
                        return;
                    } else {
                        Alert alert = new Alert("Helaas", "Deze dobbelsteen kan niet in waarde verhoogd worden", AlertType.ERROR);
                        myScene.addAlertPane(alert);
                    }
                }
            }
        }
    }

    public void decreaseEyes(){
        GameDieDao gameDieDao = new GameDieDao();
        if (activeToolCard != null) {
            for (int index = 0; index < dieViews.size(); index++) {
                GameDie gameDie = dieViews.get(index).getGameDie();
                if (dieViews.get(index).isSelected() == true) {
                    //decrease die eye value
                    if (gameDie.getEyes() != 1) {
                        gameDie.setEyes(gameDie.getEyes() - 1);
                        gameDieDao.updateDie(game, gameDie);
                        myScene.removePopupPane();
                        activeToolCard = null;
                        playerController.viewGame();
                        return;
                    } else {
                        Alert alert = new Alert("Helaas", "Deze dobbelsteen kan niet in waarde verlaagd worden", AlertType.ERROR);
                        myScene.addAlertPane(alert);
                    }
                }
            }
        }
    }
}