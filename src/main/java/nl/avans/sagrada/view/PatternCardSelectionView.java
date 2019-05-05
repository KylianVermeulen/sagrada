package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public class PatternCardSelectionView extends StackPane implements ViewInterface {
    private PlayerController playerController;
    private ArrayList<PatternCard> optionalPatternCards;

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
        Label label = new Label("Selecteer een patroonkaart om mee te spelen");
        label.setFont(new Font("Segoe Script", 22));
        StackPane.setAlignment(label, Pos.TOP_CENTER);
        StackPane.setMargin(label, new Insets(70, 0, 0, 0));

        TilePane tilePane = new TilePane();
        tilePane.setMaxSize(PatternCardView.PATTERNCARD_WIDTH * 2 + 40,
                PatternCardView.PATTERNCARD_HEIGHT * 2 + 40);
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        StackPane.setAlignment(tilePane, Pos.CENTER);

        for (PatternCard patternCard : optionalPatternCards) {
            PatternCardView patternCardView = new PatternCardView(playerController);
            patternCardView.setPatternCard(patternCard);
            patternCardView.setOnMouseClicked(e -> playerController.actionSelectPatternCard(patternCard));
            patternCardView.render();
            tilePane.getChildren().add(patternCardView);
        }
        getChildren().add(label);
        getChildren().add(tilePane);
    }
}
