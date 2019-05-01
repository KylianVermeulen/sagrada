package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.RoundTrack;

public class RoundTrackView extends HBox {

    private RoundTrack roundTrack;
    private RoundTrackFieldView[] roundTrackFieldViews;

    public RoundTrackView(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
        setPadding(new Insets(5));

        // ADD FINAL INT 10
        roundTrackFieldViews = new RoundTrackFieldView[10];

        setPrefSize(400, 100);
        setMaxSize(400, 100);
        setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        makeTrackFieldViews();
    }

    private void makeTrackFieldViews() {
        for (int i = 0; i < roundTrackFieldViews.length; i++) {
            roundTrackFieldViews[i] = new RoundTrackFieldView();
            Pane paddingPane = new Pane();
            paddingPane.setPadding(new Insets(3));
            paddingPane.getChildren().add(roundTrackFieldViews[i]);
            getChildren().add(paddingPane);
        }
    }
}
