package nl.avans.sagrada.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.view.PatternCardView;

import java.util.Random;

public class MyScene extends Scene {

    private PatternCard patternCard;
    private PatternCardView patternCardView;

    public MyScene() {
        super(new Pane());
        patternCardView = new PatternCardView();
        Random rnd = new Random();
        for(int i = 0; i < 10;i++){
            int xPos = rnd.nextInt(4);
            int yPos = rnd.nextInt(3);
            int eyes = rnd.nextInt(6) + 1;
            System.out.println("e: " + eyes + " x: " + xPos + " y: " + yPos);

            if(!patternCardView.hasFieldAttributes(xPos, yPos)){
                patternCardView.setEyes(eyes, xPos,yPos);
                patternCardView.addEyes(xPos, yPos);
            }
        }


//        patternCardView.setEyes(2,1,3);
//        patternCardView.setColor("purple",1,3);

        TilePane rootPane = new TilePane();
        rootPane.getChildren().add(patternCardView);
        setRoot(rootPane);
    }


}
