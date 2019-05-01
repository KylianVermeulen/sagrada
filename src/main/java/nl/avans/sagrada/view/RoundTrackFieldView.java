package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class RoundTrackFieldView extends TilePane {

    private Pane[] panes;

    public RoundTrackFieldView() {
        panes = new Pane[9];
        setPrefSize(30, 80);
        setPadding(new Insets(3));
        setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        makeSquares();
    }

    private void makeSquares() {
        for (int i = 0; i < panes.length; i++) {
            Pane pane = new Pane();
            Pane paddingPane = new Pane();
            paddingPane.setPadding(new Insets(3));
            pane.setPrefSize(20, 20);
            pane.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
            panes[i] = pane;
            paddingPane.getChildren().add(pane);
            getChildren().add(paddingPane);
        }
    }
}
