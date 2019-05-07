package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.RoundTrack;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class RoundTrackView extends HBox implements ViewInterface {
    private RoundTrack roundTrack;
    private RoundTrackFieldView[] roundTrackFieldViews;

    /**
     * Full constructor
     *
     * @param roundTrack RoundTrack
     */
    public RoundTrackView(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
        setPadding(new Insets(5, 0, 0, 5));

        // ADD FINAL INT 10
        roundTrackFieldViews = new RoundTrackFieldView[10];
        setPrefSize(740, 80);
        setBackground(new Background(new BackgroundFill(Color.GRAY, null, null)));
        makeRoundTrackFieldViews();
    }

    /**
     * Renders the RoundTrackFields
     */
    @Override
    public void render() {
        for (RoundTrackFieldView roundTrackFieldView : roundTrackFieldViews) {
            roundTrackFieldView.render();
        }
    }

    /**
     * Makes the RoundTrackFieldViews and adds them in a pane that has padding
     */
    private void makeRoundTrackFieldViews() {
        for (int i = 0; i < roundTrackFieldViews.length; i++) {
            roundTrackFieldViews[i] = new RoundTrackFieldView();
            roundTrackFieldViews[i].setRoundTrack(roundTrack);
            roundTrackFieldViews[i].setRoundTrackField(roundTrack.getRoundTrackField(i));
            Pane paddingPane = new Pane();
            paddingPane.setPadding(new Insets(3));
            paddingPane.getChildren().add(roundTrackFieldViews[i]);
            getChildren().add(paddingPane);
        }
    }
}
