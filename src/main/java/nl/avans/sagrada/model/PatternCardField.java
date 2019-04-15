package nl.avans.sagrada.model;

import javafx.scene.paint.Color;

public class PatternCardField {
    private GameDie die;
    private int positionX;
    private int positionY;
    private String color;
    private int value;
    private PatternCard patternCard;

    public boolean canPlaceDieOnField(GameDie die) {
        return false;
    }

    public boolean hasColor(){
        if(color == null){
            return false;
        }
        return true;
    }

    public boolean hasEyes(){
        if(value == 0){
            return false;
        }
        return true;
    }

    public Color getColor(){
        return Color.valueOf(color);
    }
}
