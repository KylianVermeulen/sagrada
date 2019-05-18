package nl.avans.sagrada.model.toolcard;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.dao.GameDieDao;
import nl.avans.sagrada.model.*;
import nl.avans.sagrada.view.DieOfferView;
import nl.avans.sagrada.view.DieView;
import nl.avans.sagrada.view.popups.Popup;

public class ToolCardDriePuntStang extends ToolCard {
    GameDie targetDie;

    public ToolCardDriePuntStang(int id, String name, int seqnr, String description) {
        super(id, name, seqnr, description);
    }

    @Override
    public PatternCard handleDrag(MouseEvent event, GameDie die) {
        return null;
    }

    @Override
    public GameDie handleClick(MouseEvent event, Game game, PlayerController playerController, Pane pane) {
        try{
            DieView dieView = (DieView) event.getTarget();
            targetDie = dieView.getGameDie();
            Popup popup = new Popup(300, 300, 300, 300) {
                @Override
                public void render() {
                    BorderPane borderPane = new BorderPane();
                    borderPane.setMaxHeight(300);
                    borderPane.setMinHeight(300);
                    borderPane.setMaxWidth(300);
                    borderPane.setMinWidth(300);
                    Pane buttonPane = new Pane();
                    buttonPane.setMaxHeight(70);
                    buttonPane.setMinHeight(70);
                    buttonPane.setMaxWidth(300);
                    buttonPane.setMinWidth(300);
                    Button plusButton = new Button("+");
                    plusButton.setOnAction(e -> {
                        increaseEyes(targetDie);
                    });
                    Button minButton = new Button("-");
                    minButton.setOnAction(e -> {
                        decreaseEyes(targetDie);
                    });
                    buttonPane.getChildren().addAll(plusButton, minButton);
                    borderPane.setCenter(pane);
                    borderPane.setBottom(buttonPane);
                    getChildren().add(borderPane);
                }
            };
            popup.render();
            playerController.getMyScene().addPopupPane(popup);
        } catch (Exception e){
        }

        return targetDie;
    }

    private void increaseEyes(GameDie targetDie){
        targetDie.setEyes(targetDie.getEyes() + 1);
    }

    private void decreaseEyes(GameDie targetDie){
        targetDie.setEyes(targetDie.getEyes() - 1);
    }
}
