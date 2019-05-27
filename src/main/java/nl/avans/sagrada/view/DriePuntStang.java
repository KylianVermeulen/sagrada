package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private static final int POPUP_HEIGHT = 350;
    private static final int POPUP_WIDTH = 500;
    private final int BORDER_PANE_HEIGHT = 350;
    private final int BORDER_PANE_WIDTH = 500;
    private final int BUTTON_PANE_HEIGHT = 70;
    private final int BUTTON_PANE_SPACING = 10;
    private final int BUTTON_HEIGHT = 25;
    private final int BUTTON_WIDTH = 50;
    private MyScene myScene;
    private ArrayList<DieView> dieViews;
    private ArrayList<GameDie> gameDice;
    private Game game;
    private PlayerController playerController;
    private ToolCard activeToolCard;
    private HBox diePane;
    private VBox rootPane;
    private BorderPane textPane;
    private Button plusButton;
    private Button minButton;
    private HBox buttonPane;
    private Text uitlegText;

    public DriePuntStang(MyScene myScene, PlayerController playerController, Game game, ToolCard activeToolCard) {
        super(POPUP_WIDTH, POPUP_HEIGHT);
        this.myScene = myScene;
        this.playerController = playerController;
        this.game = game;
        this.activeToolCard = activeToolCard;

        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        this.render();
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("ToolCardDriePuntTang");
    }

    @Override
    public void render() {
        getChildren().clear();
        buildButtons();
        buildButtonPane();
        buildDieView();
        buildText();
        buildBorderPane();
        setOnBorderClicked();
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
        plusButton.getStyleClass().add("DriePuntTangButton");
        plusButton.setOnAction(e -> {
            increaseEyes();
        });
        minButton = new Button("-");
        minButton.setMaxHeight(BUTTON_HEIGHT);
        minButton.setMinHeight(BUTTON_HEIGHT);
        minButton.setMaxWidth(BUTTON_WIDTH);
        minButton.setMinWidth(BUTTON_WIDTH);
        minButton.getStyleClass().add("DriePuntTangButton");
        minButton.setOnAction(e -> {
            decreaseEyes();
        });
    }

    /**
     * method to build the ButtonPane
     */
    private void buildButtonPane() {
        buttonPane = new HBox();
        buttonPane.setSpacing(BUTTON_PANE_SPACING);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setMaxHeight(BUTTON_PANE_HEIGHT);
        buttonPane.setMinHeight(BUTTON_PANE_HEIGHT);
        buttonPane.getChildren().addAll(plusButton, minButton);
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

        borderPane.setAlignment(textPane, Pos.CENTER);
        borderPane.setTop(textPane);
        borderPane.setAlignment(rootPane, Pos.CENTER);
        borderPane.setCenter(rootPane);
        borderPane.setAlignment(buttonPane, Pos.CENTER);
        borderPane.setBottom(buttonPane);
        getChildren().add(borderPane);
    }

    private void buildText(){
        uitlegText = new Text();
        uitlegText.setText("Klik op een dobbelsteen en verlaag of verhoog het aantal ogen met de knoppen");
        uitlegText.getStyleClass().add("DriePuntTangText");
        textPane = new BorderPane();
        textPane.setCenter(uitlegText);
        textPane.setMaxHeight(25);
        textPane.setMinHeight(25);
    }

    private void buildDieView() {
        String css = this.getClass().getResource("/css/DieViewSelection.css").toExternalForm();
        dieViews = new ArrayList<>();
        gameDice = new ArrayList<>();

        diePane = new HBox();
        diePane.setAlignment(Pos.CENTER);
        rootPane = new VBox();
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPrefSize(super.getWidth(), super.getHeight());
        for (GameDie gameDie : game.getRoundDice()) {
            Pane paddingPane = new Pane();
            DieView dieView = new DieView(gameDie);
            dieViews.add(dieView);
            gameDice.add(gameDie);
            dieView.resize(25, 25);
            paddingPane.setPadding(new Insets(20));
            dieView.render();
            paddingPane.getChildren().add(dieView);
            diePane.getChildren().add(paddingPane);
        }
        rootPane.getChildren().add(diePane);
    }

    /**
     * Increases the eye value of a selected die
     */
    private void increaseEyes() {
        GameDieDao gameDieDao = new GameDieDao();
        if (activeToolCard != null) {
            for (int index = 0; index < dieViews.size(); index++) {
                GameDie gameDie = dieViews.get(index).getGameDie();
                //increase die eye value
                if (dieViews.get(index).getStylesheets().size() != 0) {
                    if (gameDie.getEyes() != 6) {
                        gameDie.setEyes(gameDie.getEyes() + 1);
                        gameDieDao.updateDieEyes(game, gameDie);
                        myScene.removePopupPane();
                        activeToolCard.setIsDone(true);
                        activeToolCard = null;
                        playerController.setActiveToolCardNull();
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

    /**
     * Decreases the eye value of a selected die
     */
    private void decreaseEyes(){
        GameDieDao gameDieDao = new GameDieDao();
        if (activeToolCard != null) {
            for (int index = 0; index < dieViews.size(); index++) {
                GameDie gameDie = dieViews.get(index).getGameDie();
                if (dieViews.get(index).getStylesheets().size() != 0) {
                    //decrease die eye value
                    if (gameDie.getEyes() != 1) {
                        gameDie.setEyes(gameDie.getEyes() - 1);
                        gameDieDao.updateDieEyes(game, gameDie);
                        myScene.removePopupPane();
                        activeToolCard.setIsDone(true);
                        playerController.setActiveToolCardNull();
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

    /**
     * sets the mouseclick action for the dieviews
     */
    private void setOnBorderClicked(){
        String css = this.getClass().getResource("/css/DieViewSelection.css").toExternalForm();
        for (DieView dieView : dieViews){
            dieView.setOnMouseClicked(e -> {
                dieView.getStylesheets().add(css);
                dieView.getStyleClass().add("DriePuntStangDie");
                for(DieView dieView1 : dieViews){
                    if(dieView.getGameDie() != dieView1.getGameDie()){
                        dieView1.getStyleClass().clear();
                        dieView1.getStylesheets().clear();
                    }
                }
            });
        }
    }
}