package nl.avans.sagrada.view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import nl.avans.sagrada.controller.PlayerController;

public class PrivateObjectiveCardView extends CardView {
    private final static int SQUAREWIDTH = 100;
    private final static int SQUAREHEIGHT = 100;
    
    private PlayerController playerController;
    
    public PrivateObjectiveCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
        render();
    }

    @Override
    public void render() {
        getChildren().clear();
        showSquare();
    }

    private void showSquare() {
        Rectangle colorSquare = new Rectangle();
        colorSquare.setWidth(SQUAREWIDTH);
        colorSquare.setHeight(SQUAREHEIGHT);
        colorSquare.setFill(Color.BLUE);
        
        setCenter(colorSquare);
        
    }

}
