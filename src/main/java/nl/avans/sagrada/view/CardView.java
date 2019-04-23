package nl.avans.sagrada.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public abstract class CardView extends BorderPane implements ViewInterface {
    private static final int CARD_WIDTH = 130;
    private static final int CARD_HEIGHT = 170;
    private static final Color CARDSANDYELLOW = Color.rgb(255, 240, 173);
    
    /**
     * Empty constructor
     */
    public CardView() {
        setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        setBackground(new Background(new BackgroundFill(CARDSANDYELLOW, null, null)));
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("CardView");
    }
    
    /**
     * Returns the standard height for cards.
     * @return standard card height (int)
     */
    public int getViewHeight() {
        return CARD_HEIGHT;
    }
    
    /**
     * Returns the standard width for cards.
     * @return standard card width (int)
     */
    public int getViewWidth() {
        return CARD_WIDTH;
    }
}
