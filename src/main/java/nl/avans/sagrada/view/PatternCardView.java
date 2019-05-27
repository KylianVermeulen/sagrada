package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class PatternCardView extends BorderPane implements ViewInterface {
    public static final int PATTERNCARD_WIDTH = 260;
    public static final int PATTERNCARD_HEIGHT = 190;
    private final int PLAYER_COLOR_SIZE = 7;
    private final int PLAYER_NAME_PANE_SPACING = 5;
    private PatternCard patternCard;
    private PlayerController playerController;
    private PatternCardFieldView[][] patternCardFieldViews;
    private TilePane patternCardField;
    private HBox difficultyBar;
    private HBox playerNamePane;
    private BorderPane bottomPane;
    private String playerName;
    private Color playerColor;
    private boolean currentPlayer;
    private final Font PLAYER_NAME_FONT = new Font("Segoe Script", 20);

    /**
     * Partial constructor
     *
     * @param playerController PlayerController
     */
    public PatternCardView(PlayerController playerController) {
        this.playerController = playerController;
        bottomPane = new BorderPane();
        setPrefSize(PATTERNCARD_WIDTH, PATTERNCARD_HEIGHT);
        setMaxSize(PATTERNCARD_WIDTH, PATTERNCARD_HEIGHT);
        setPadding(new Insets(10, 0, 0, 10));
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
    }

    /**
     * Set PatternCard to view
     *
     * @param patternCard PatternCard
     */
    public void setPatternCard(PatternCard patternCard) {
        this.patternCard = patternCard;
    }

    /**
     * Renders the view with all the information
     */
    @Override
    public void render() {
        getChildren().clear();
        patternCardField = new TilePane();
        patternCardFieldViews = new PatternCardFieldView[PatternCard.CARD_SQUARES_WIDTH + 1][
                PatternCard.CARD_SQUARES_HEIGHT + 1];
        makePatternCardFieldViews();

        setCenter(patternCardField);
        showPlayerName();
        showDifficulty();
        setBottom(bottomPane);
    }

    /**
     * Shows difficulty view
     */
    private void showDifficulty() {
        difficultyBar = new HBox();
        difficultyBar.setPadding(new Insets(5, 0, 5, 5));

        for (int i = 0; i < patternCard.getDifficulty(); i++) {
            Pane pane = new Pane();
            pane.setPadding(new Insets(0, 5, 0, 0));
            Circle circle = new Circle(5, Color.WHITE);
            pane.getChildren().add(circle);
            difficultyBar.getChildren().add(pane);
        }

        GridPane difficultyBarWrapper = new GridPane();
        difficultyBarWrapper.getChildren().add(difficultyBar);
        difficultyBarWrapper.setAlignment(Pos.BOTTOM_RIGHT);
        bottomPane.setRight(difficultyBarWrapper);
    }

    private void showPlayerName() {
        playerNamePane = new HBox();
        Text name = new Text(playerName);
        if (!currentPlayer) {
            name.setFill(Color.WHITE);
        } else {
            name.setFill(Color.YELLOW);
        }
        name.setFont(PLAYER_NAME_FONT);
        Circle color = new Circle(PLAYER_COLOR_SIZE);
        color.setFill(playerColor);
        playerNamePane.getChildren().addAll(color, name);
        playerNamePane.setAlignment(Pos.CENTER);
        playerNamePane.setSpacing(PLAYER_NAME_PANE_SPACING);
        bottomPane.setLeft(playerNamePane);
    }

    public void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * Sets the color of the player on the patterncard
     * @param playerColor
     */
    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Sets if the pattern card is of the current players turn.
     *
     * @param currentPlayer True when current player.
     */
    public void setCurrentPlayer(boolean currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Makes a 2D Array with the PatternCardField and their positions
     */
    private void makePatternCardFieldViews() {
        for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) {
                PatternCardFieldView patternCardFieldView = new PatternCardFieldView(
                        playerController);
                patternCardFieldView.setPatternCard(patternCard);
                patternCardFieldView.setPatternCardField(patternCard.getPatternCardField(x, y));
                patternCardFieldView.render();
                patternCardFieldViews[x][y] = patternCardFieldView;

                Pane paddingPane = new Pane();
                paddingPane.setPadding(new Insets(5));
                paddingPane.getChildren().add(patternCardFieldView);
                patternCardField.getChildren().add(paddingPane);
            }
        }
    }
}
