package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class PatternCardSelectionView extends BorderPane implements ViewInterface {
    private PlayerController playerController;
    private ArrayList<PatternCard> optionalPatternCards;
    private ArrayList<PatternCardView> patternCardViews = new ArrayList<>();

    public PatternCardSelectionView(PlayerController playerController) {
        this.playerController = playerController;
        setPrefSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
    }

    public void setOptionalPatternCards(ArrayList<PatternCard> optionalPatternCards) {
        this.optionalPatternCards = optionalPatternCards;
    }

    @Override
    public void render() {
        getChildren().clear();
        StackPane stackPane = new StackPane();
        Label label = new Label("Selecteer een patroonkaart om mee te spelen");
        label.setFont(new Font("Segoe Script", 22));
        stackPane.setAlignment(label, Pos.TOP_CENTER);
        stackPane.setMargin(label, new Insets(70, 0, 0, 0));

        TilePane tilePane = new TilePane();
        tilePane.setMaxSize(PatternCardView.PATTERNCARD_WIDTH * 2 + 80,
                PatternCardView.PATTERNCARD_HEIGHT * 2 + 80);
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        stackPane.setAlignment(tilePane, Pos.CENTER);

        for (PatternCard patternCard : optionalPatternCards) {
            PatternCardView patternCardView = new PatternCardView(playerController);
            patternCardView.setPatternCard(patternCard);
            patternCardView.render();
            patternCardView.setPrefWidth(PatternCardView.PATTERNCARD_WIDTH + 7);
            patternCardView.setMaxWidth(PatternCardView.PATTERNCARD_WIDTH + 7);
            patternCardViews.add(patternCardView);
            tilePane.getChildren().add(patternCardView);
        }
        setBorderOnClicked();

        Button button = new Button();
        button.setText("Opslaan");
        button.setOnAction(e -> {
            int i = 0;
            for (PatternCardView patternCardView : patternCardViews) {
                if (patternCardView.getBorder() != null) {
                    playerController.actionSelectPatternCard(optionalPatternCards.get(i));
                    return;
                }
                i++;
            }
        });
        button.setPrefSize(100, 40);
        BorderPane chooseCardButton = new BorderPane();
        chooseCardButton.setCenter(button);
        chooseCardButton.setPadding(new Insets(50, 50, 50, 50));

        PrivateObjectiveCardView privateObjectiveCard = new PrivateObjectiveCardView(
                playerController.getPlayer());
        privateObjectiveCard.viewWithoutText();

        Pane privateObjectiveCardView = new Pane();
        privateObjectiveCard.setPadding(new Insets(300, 50, 50, 50));
        privateObjectiveCardView.getChildren().add(privateObjectiveCard);

        stackPane.getChildren().add(label);
        stackPane.getChildren().add(tilePane);
        this.setCenter(stackPane);
        this.setLeft(privateObjectiveCardView);
        this.setRight(chooseCardButton);

    }

    /**
     * Adds a border to the clicked patternCard and removes them from the others
     */
    private void setBorderOnClicked() {
        patternCardViews.get(0).setOnMouseClicked(e -> {
            patternCardViews.get(0).setBorder(new Border(
                    new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                            new BorderWidths(3.5))));
            patternCardViews.get(1).setBorder(null);
            patternCardViews.get(2).setBorder(null);
            patternCardViews.get(3).setBorder(null);

        });

        patternCardViews.get(1).setOnMouseClicked(e -> {
            patternCardViews.get(1).setBorder(new Border(
                    new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                            new BorderWidths(3.5))));
            patternCardViews.get(0).setBorder(null);
            patternCardViews.get(2).setBorder(null);
            patternCardViews.get(3).setBorder(null);
        });

        patternCardViews.get(2).setOnMouseClicked(e -> {
            patternCardViews.get(2).setBorder(new Border(
                    new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                            new BorderWidths(3.5))));
            patternCardViews.get(1).setBorder(null);
            patternCardViews.get(0).setBorder(null);
            patternCardViews.get(3).setBorder(null);
        });

        patternCardViews.get(3).setOnMouseClicked(e -> {
            patternCardViews.get(3).setBorder(new Border(
                    new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                            new BorderWidths(3.5))));
            patternCardViews.get(1).setBorder(null);
            patternCardViews.get(2).setBorder(null);
            patternCardViews.get(0).setBorder(null);

        });
    }
}