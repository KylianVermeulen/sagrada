package nl.avans.sagrada.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public abstract class CardView extends BorderPane implements ViewInterface {
    private static final int WIDTH = 310;
    private static final int HEIGHT = 440;
    private static final Color CARDSANDYELLOW = Color.rgb(255, 240, 173);
    
    public CardView() {
        super();
        setPrefSize(WIDTH, HEIGHT);
        setBackground(new Background(new BackgroundFill(CARDSANDYELLOW, null, null)));
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("CardView");
    }
    
    public int getViewHeight() {
        return HEIGHT;
    }
    
    public int getViewWidth() {
        return WIDTH;
    }
    
}
