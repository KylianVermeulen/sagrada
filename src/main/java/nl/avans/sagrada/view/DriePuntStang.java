package nl.avans.sagrada.view;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.view.popups.Popup;

public class DriePuntStang extends Popup {
    private final int BORDERPANE_HEIGHT = 800;
    private final int BORDERPANE_WIDTH = 1280;
    private final int BUTTONPANE_HEIGHT = 70;
    private final int BUTTONPANE_WIDTH = 1280;
    private final int BUTTON_HEIGHT = 25;
    private final int BUTTON_WIDTH = 50;
    private MyScene myScene;
    private PlayerController playerController;
    private DieView dieView;
    private MouseEvent event;
    private Button plusButton;
    private Button minButton;
    private Button backButton;
    private BorderPane buttonPane;

    public DriePuntStang(MyScene myScene, PlayerController playerController, MouseEvent event, DieView dieView){
        super(0, 0,1280, 800);
        this.myScene = myScene;
        this.playerController = playerController;
        this.event = event;
        this.dieView = dieView;

        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

        this.render();
    }

    @Override
    public void render() {
        buildButtons();
        buildButtonPane();
        buildBorderPane();
    }

    /**
     * Method to build the buttons
     */
    private void buildButtons(){
        plusButton = new Button("+");
        plusButton.setMaxHeight(BUTTON_HEIGHT);
        plusButton.setMinHeight(BUTTON_HEIGHT);
        plusButton.setMaxWidth(BUTTON_WIDTH);
        plusButton.setMinWidth(BUTTON_WIDTH);
        plusButton.setOnAction(e -> {
            playerController.actionChangeDieEyes(event, 1);
        });
        minButton = new Button("-");
        minButton.setMaxHeight(BUTTON_HEIGHT);
        minButton.setMinHeight(BUTTON_HEIGHT);
        minButton.setMaxWidth(BUTTON_WIDTH);
        minButton.setMinWidth(BUTTON_WIDTH);
        minButton.setOnAction(e -> {
            playerController.actionChangeDieEyes(event, 0);
        });
        backButton = new Button("cancel");
        backButton.setMaxHeight(BUTTON_HEIGHT);
        backButton.setMinHeight(BUTTON_HEIGHT);
        backButton.setMaxWidth(BUTTON_WIDTH);
        backButton.setMinWidth(BUTTON_WIDTH);
        backButton.setOnAction(e -> {
            myScene.removePopupPane();
        });
    }

    /**
     * method to build the ButtonPane
     */
    private void buildButtonPane(){
        buttonPane = new BorderPane();
        buttonPane.setMaxHeight(BUTTONPANE_HEIGHT);
        buttonPane.setMinHeight(BUTTONPANE_HEIGHT);
        buttonPane.setMaxWidth(BUTTONPANE_WIDTH);
        buttonPane.setMinWidth(BUTTONPANE_WIDTH);

        HBox centerButtonsPane = new HBox();
        centerButtonsPane.getChildren().addAll(plusButton, minButton);
        buttonPane.setLeft(backButton);
        buttonPane.setCenter(centerButtonsPane);
    }

    /**
     * Method to build the BorderPane
     */
    private void buildBorderPane(){
        BorderPane borderPane = new BorderPane();
        borderPane.setMaxHeight(BORDERPANE_HEIGHT);
        borderPane.setMinHeight(BORDERPANE_HEIGHT);
        borderPane.setMaxWidth(BORDERPANE_WIDTH);
        borderPane.setMinWidth(BORDERPANE_WIDTH);

        borderPane.setCenter(dieView);
        borderPane.setBottom(buttonPane);
        getChildren().add(borderPane);
    }
}