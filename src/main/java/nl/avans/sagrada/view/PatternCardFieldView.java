package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;

public class PatternCardFieldView extends StackPane {
    private int x;
    private int y;
    private Text text;
    private PatternCardField patternCardField;
    public PatternCardFieldView(PatternCard patternCard){
        text = new Text();
        text.setStyle("-fx-font-size: 10px");
        setPrefSize(50,50);
        setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
        text.setFill(Color.WHITE);
        patternCardField = patternCard.getPatternCardField(x,y);
        if(patternCardField.hasColor()){
            addColor();
        }
        if(patternCardField.hasEyes()){

        }
        getChildren().add(text);
    }

    private void addColor() {
        setBackground(new Background(new BackgroundFill(patternCardField.getColor(),null,null)));
    }

    public void setY(int y) {
        this.y = y;
        updateText();
    }

    private void updateText() {
        text.setText("x: " + x + "y: " + y);
    }

    public void setX(int x) {
        this.x = x;
        updateText();
    }
}
