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
    private final int highestScore;
    private final String mostUsedColor;
    private final int mostUsedValue;
    private final int countAccounts;

    public Stats(MyScene myScene, Account account) {
        super(WIDTH_STATS, HEIGHT_STATS);
        this.myScene = myScene;

        int[] winsLoses = account.getWinLoseCount();
        this.wins = winsLoses[0];
        this.loses = winsLoses[1];
        this.highestScore = account.getHighestScore();
        this.mostUsedColor = account.getMostUsedColor();
        this.mostUsedValue= account.getMostUsedValue();
        this.countAccounts = account.getCountDifferentPlayedAccounts();

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

        Label label1 = new Label("win:lose : " + wins + ":" + loses);
        label1.setTextFill(Color.web("#000"));
        label1.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label label2 = new Label("Hoogste score: " + highestScore);
        label2.setTextFill(Color.web("#000"));
        label2.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label label3 = new Label("Meest gebruikte kleur: " + mostUsedColor);
        label3.setTextFill(Color.web("#000"));
        label3.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label label4 = new Label("Meest gebruikte value: " + mostUsedValue);
        label4.setTextFill(Color.web("#000"));
        label4.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label label5 = new Label("Aantal gespeelde accounts: " + countAccounts);
        label5.setTextFill(Color.web("#000"));
        label5.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Button button = new Button("Close");
        button.setOnAction(e -> myScene.removePopupPane());
        VBox.setMargin(button, new Insets(20, 0, 0, 0));

        vBox.getChildren().add(label1);
        vBox.getChildren().add(label2);
        vBox.getChildren().add(label3);
        vBox.getChildren().add(label4);
        vBox.getChildren().add(label5);
        vBox.getChildren().add(button);
        getChildren().add(vBox);
    }
}
