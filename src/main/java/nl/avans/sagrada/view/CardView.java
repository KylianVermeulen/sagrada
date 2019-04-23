package nl.avans.sagrada.view;

import javafx.scene.layout.BorderPane;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public abstract class CardView extends BorderPane implements ViewInterface {
    private static final int WIDTH = 310;
    private static final int HEIGHT = 440;
    
    /**
     * Empty constructor
     */
    public CardView() {
        super();
        setPrefSize(WIDTH, HEIGHT);
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("CardView");
    }
    
    /**
     * Returns the standard height for cards.
     * @return standard card height (int)
     */
    public int getViewHeight() {
        return HEIGHT;
    }
    
    /**
     * Returns the standard width for cards.
     * @return standard card width (int)
     */
    public int getViewWidth() {
        return WIDTH;
    }
}
