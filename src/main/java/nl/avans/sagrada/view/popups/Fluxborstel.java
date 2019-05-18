package nl.avans.sagrada.view.popups;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.view.MyScene;

public class Fluxborstel extends Popup {
    public static final int WIDTH_FLUXBORSTEL = 630;
    public static final int HEIGHT_FLUXBORSTEL = 340;

    private MyScene myScene;
    private Game game;

    public Fluxborstel(MyScene myScene, Game game) {
        super(WIDTH_FLUXBORSTEL, HEIGHT_FLUXBORSTEL);
        this.myScene = myScene;
        this.game = game;

        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#fff"), new CornerRadii(26), null)));
        this.render();
    }

    @Override
    public void render() {
        Button button = new Button("Close");
        button.setOnAction(e -> myScene.removePopupPane());
        getChildren().add(button);
    }
}
