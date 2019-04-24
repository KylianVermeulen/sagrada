package nl.avans.sagrada.view;

import javafx.scene.layout.BorderPane;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public abstract class CardView extends BorderPane implements ViewInterface {
    public static final int CARD_WIDTH = 130;
    public static final int CARD_HEIGHT = 170;
    
    /**
     * Empty constructor
     */
    public CardView() {
        setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("CardView");
    }
}
