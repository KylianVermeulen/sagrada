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
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class PatternCardView extends BorderPane implements ViewInterface {
    public static final int PATTERNCARD_WIDTH = 260;
    public static final int PATTERNCARD_HEIGHT = 190;
    private PatternCard patternCard;
    private PlayerController playerController;
    private PatternCardFieldView[][] patternCardFieldViews;
    private TilePane patternCardField;
    private HBox difficultyBar;

    /**
     * Partial constructor
     *
     * @param playerController PlayerController
     */
    public PatternCardView(PlayerController playerController) {
        this.playerController = playerController;
        setPrefSize(PATTERNCARD_WIDTH, PATTERNCARD_HEIGHT);
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
        showDifficulty();
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
        setBottom(difficultyBarWrapper);
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
