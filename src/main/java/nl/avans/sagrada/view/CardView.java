package nl.avans.sagrada.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public abstract class CardView extends BorderPane implements ViewInterface {
    public static final int CARD_WIDTH = 130;
    public static final int CARD_HEIGHT = 170;
    public static final int ZOOM_CARD_WIDTH = 260;
    public static final int ZOOM_CARD_HEIGHT = 340;
    
    /**
     * Empty constructor
     */
    public CardView() {
        setPrefSize(CARD_WIDTH, CARD_HEIGHT);
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

    public int getZoomViewWidth() {
        return ZOOM_CARD_WIDTH;
    }

    public int getZoomViewHeight() {
        return ZOOM_CARD_HEIGHT;
    }
}
