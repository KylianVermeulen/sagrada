package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import nl.avans.sagrada.model.PatternCard;

import static nl.avans.sagrada.Main.CARD_HEIGHT;
import static nl.avans.sagrada.Main.CARD_WIDTH;

public class PatternCardView extends BorderPane {
    private PatternCard patternCard;
    private PatternCardFieldView[][] card;
    private TilePane patternCardField;
    private HBox difficultyBar;
    public PatternCardView() {
        setPadding(new Insets(10, 0, 0, 10));
        setPrefSize(310, 230);
        setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        difficultyBar = new HBox();
        patternCardField = new TilePane();
        difficultyBar.setPadding(new Insets(5,0,5,5));
        patternCard = new PatternCard();
        card = new PatternCardFieldView[CARD_WIDTH][CARD_HEIGHT];
        makeCard();
        showDifficulty();
        setCenter(patternCardField);
    }

    private void showDifficulty() {
        for(int i = 0; i < patternCard.getDifficulty(); i++){
            Pane pane = new Pane();
            pane.setPadding(new Insets(0,5,0,0));
            Circle circle = new Circle(5,Color.WHITE);
            pane.getChildren().add(circle);
            difficultyBar.getChildren().add(pane);
        }
        GridPane test = new GridPane();
        test.getChildren().add(difficultyBar);
        test.setAlignment(Pos.BOTTOM_RIGHT);
        setBottom(test);
    }

    public void addColor(int x, int y) {
        card[x][y].addColor();
    }

    public void addEyes(int x, int y) {
        card[x][y].addEyes();
    }


    public void setEyes(int eyes, int x, int y) {
        card[x][y].setEyes(eyes);
    }

    public void setColor(String color, int x, int y) {
        card[x][y].setColor(color);

    }

    private void makeCard() {
        for (int y = 0; y < CARD_HEIGHT; y++) {
            for (int x = 0; x < CARD_WIDTH; x++) {
                PatternCardFieldView patternCardFieldView = new PatternCardFieldView(patternCard);
                card[x][y] = patternCardFieldView;
                patternCardFieldView.setY(y);
                patternCardFieldView.setX(x);
                patternCardFieldView.initPatternCardField();
                Pane paddingPane = new Pane();
                paddingPane.setPadding(new Insets(5, 5, 5, 5));
                paddingPane.getChildren().add(patternCardFieldView);
                patternCardField.getChildren().add(paddingPane);
            }
        }
    }

    public void setDice(){
        // PLACE DICE METHOD HERE
    }

    public void addDie(int x, int y){
        card[x][y].addDie();
    }

    public boolean hasFieldAttributes(int x, int y) {
        return card[x][y].hasAttributes();
    }

    public PatternCard getPatternCard() {
        return this.patternCard;
    }

    public boolean checkSides(int xPos, int yPos, String color) {
        return patternCard.checkSides(xPos,yPos,color);
    }
}
