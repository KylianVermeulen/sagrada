package nl.avans.sagrada.view;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.RoundTrack;
import nl.avans.sagrada.model.RoundTrackField;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class RoundTrackFieldView extends TilePane implements ViewInterface {
    private Pane[] panes;
    private RoundTrack roundTrack;
    private RoundTrackField roundTrackField;

    /**
     * Full Constructor
     */
    public RoundTrackFieldView() {
        panes = new Pane[9];
        setPrefSize(66, 66);
        setMaxSize(70, 70);
        setPadding(new Insets(3, 0, 0, 3));
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    }

    /**
     * Renders the RoundTrackField
     */
    @Override
    public void render() {
        getChildren().clear();
        makeSquares();
        replacePaneToDie();
        addPanes();
    }

    /**
     * Makes the squares in the RoundTrackField
     */
    private void makeSquares() {
        for (int i = 0; i < panes.length; i++) {
            Pane pane = new Pane();
            Pane paddingPane = new Pane();
            paddingPane.setPadding(new Insets(1));
            pane.setPrefSize(20, 20);
            pane.setMaxSize(20, 20);
            pane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
            pane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            panes[i] = pane;
            paddingPane.getChildren().add(pane);
            getChildren().add(paddingPane);
        }
    }

    /**
     * Refreshes the panes
     */
    private void addPanes() {
        getChildren().clear();
        getChildren().addAll(panes);
    }

    /**
     * Sets the RoundTrack to the RoundTrackFieldView
     *
     * @param roundTrack RoundTrack
     */
    public void setRoundTrack(RoundTrack roundTrack) {
        this.roundTrack = roundTrack;
    }

    /**
     * Gives the RoundTrackField to the RoundTrackFieldView
     *
     * @param roundTrackField RoundTrackField
     */
    public void setRoundTrackField(RoundTrackField roundTrackField) {
        this.roundTrackField = roundTrackField;
    }

    /**
     * Replaces the panes with dies if there is a die in the array
     */
    private void replacePaneToDie() {
        GameDie[] gameDies = roundTrackField.getGameDies();
        for (int i = 0; i < panes.length; i++) {
            if (gameDies[i] != null) {
                DieView dieView = new DieView(gameDies[i]);
                dieView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
                dieView.resize(20, 20);
                dieView.render();
                panes[i] = dieView;
            }
        }
    }
}
