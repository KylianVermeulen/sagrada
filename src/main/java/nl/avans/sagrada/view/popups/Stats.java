package nl.avans.sagrada.view.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import nl.avans.sagrada.model.Account;
import nl.avans.sagrada.view.MyScene;

public class Stats extends Popup {
    public static final int WIDTH_STATS = 630;
    public static final int HEIGHT_STATS = 340;
    private MyScene myScene;
    private final int wins;
    private final int loses;

    public Stats(MyScene myScene, Account account) {
        super(WIDTH_STATS, HEIGHT_STATS);
        this.myScene = myScene;

        int[] winsLoses = account.getWinLoseCount();
        this.wins = winsLoses[0];
        this.loses = winsLoses[1];

        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#fff"), new CornerRadii(26), null)));
        render();
    }

    @Override
    public void render() {
        getChildren().clear();

        VBox vBox = new VBox();
        vBox.setPrefSize(super.getwidth(), super.getheight());
        vBox.setSpacing(3);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("win:lose : " + wins + ":" + loses);
        titleLabel.setTextFill(Color.web("#000"));
        titleLabel.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Button button = new Button("Close");
        button.setOnAction(e -> myScene.removePopupPane());

        vBox.getChildren().add(titleLabel);
        vBox.getChildren().add(button);
        getChildren().add(vBox);
    }
}
