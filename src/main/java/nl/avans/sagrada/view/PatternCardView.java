package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PatternCard;

import static nl.avans.sagrada.model.PatternCard.CARD_HEIGHT;
import static nl.avans.sagrada.model.PatternCard.CARD_WIDTH;

public class PatternCardView extends BorderPane {
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
        setPrefSize(310, 230);
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

    public void render() {
        getChildren().clear();
        patternCardField = new TilePane();
        patternCardFieldViews = new PatternCardFieldView[CARD_WIDTH][CARD_HEIGHT];
        makePatternCardFieldViews();

        setCenter(patternCardField);
        showDifficulty();
    }

    /**
     * Shows difficulty view
     */
    private void showDifficulty() {
        difficultyBar = new HBox();
        difficultyBar.setPadding(new Insets(5,0,5,5));

        for (int i = 0; i < patternCard.getDifficulty(); i++) {
            Pane pane = new Pane();
            pane.setPadding(new Insets(0,5,0,0));
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
        for (int y = 0; y < CARD_HEIGHT; y++) {
            for (int x = 0; x < CARD_WIDTH; x++) {
                PatternCardFieldView patternCardFieldView = new PatternCardFieldView(playerController);
                patternCardFieldView.setPatternCard(patternCard);
                patternCardFieldView.setPatternCardField(patternCard.getPatternCardField(x,y));
                patternCardFieldView.render();
                patternCardFieldViews[x][y] = patternCardFieldView;

                Pane paddingPane = new Pane();
                paddingPane.setPadding(new Insets(5, 5, 5, 5));
                paddingPane.getChildren().add(patternCardFieldView);
                patternCardField.getChildren().add(paddingPane);
            }
        }
    }
}
