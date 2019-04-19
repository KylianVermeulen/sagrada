package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public abstract class CardView extends BorderPane implements ViewInterface {
    private static final int WIDTH = 310;
    private static final int HEIGHT = 440;
    
    public CardView() {
        super();
        setPrefSize(WIDTH, HEIGHT);
        setPadding(new Insets(10, 0, 0, 10));
        setBackground(new Background(new BackgroundFill(Color.SANDYBROWN, null, null)));
    }
    
    public int getViewHeight() {
        return HEIGHT;
    }
    
    public int getViewWidth() {
        return WIDTH;
    }
    
}
