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
    private Account account;
    private int wins;
    private int loses;
    private int highestScore;
    private String mostUsedColor;
    private int mostUsedValue;
    private int countAccounts;

    /**
     * Constructor to show the stats popup
     * Of someones stats
     * @param myScene
     * @param account
     */
    public Stats(MyScene myScene, Account account) {
        super(WIDTH_STATS, HEIGHT_STATS);
        this.myScene = myScene;
        this.account = account;

        int[] winsLoses = account.getWinLoseCount();
        this.wins = winsLoses[0];
        this.loses = winsLoses[1];
        this.highestScore = account.getHighestScore();
        this.mostUsedColor = account.getMostUsedColor();
        if (mostUsedColor == null) {
            mostUsedColor = "";
        }
        this.mostUsedValue = account.getMostUsedValue();
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

        Label labelName = new Label("Account: " +account.getUsername());
        labelName.setTextFill(Color.web("#000"));
        labelName.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));
        VBox.setMargin(labelName, new Insets(0, 0, 20, 0));

        Label labelWinLose = new Label("win:lose : " + wins + ":" + loses);
        labelWinLose.setTextFill(Color.web("#000"));
        labelWinLose.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label labelHighScore = new Label("Hoogste score: " + highestScore);
        labelHighScore.setTextFill(Color.web("#000"));
        labelHighScore.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label labelMostUsedColor = new Label("Meest gebruikte kleur: " + mostUsedColor);
        labelMostUsedColor.setTextFill(Color.web("#000"));
        labelMostUsedColor.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label labelMostUsedValue = new Label("Meest gebruikte aantal ogen: " + mostUsedValue);
        labelMostUsedValue.setTextFill(Color.web("#000"));
        labelMostUsedValue.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Label labelPlayedAccounts = new Label("Aantal gespeelde accounts: " + countAccounts);
        labelPlayedAccounts.setTextFill(Color.web("#000"));
        labelPlayedAccounts.setFont(Font.font("sans-serif", FontWeight.MEDIUM, 18));

        Button button = new Button("Close");
        button.setOnAction(e -> myScene.removePopupPane());
        VBox.setMargin(button, new Insets(20, 0, 0, 0));

        vBox.getChildren().add(labelName);
        vBox.getChildren().add(labelWinLose);
        vBox.getChildren().add(labelHighScore);
        vBox.getChildren().add(labelMostUsedColor);
        vBox.getChildren().add(labelMostUsedValue);
        vBox.getChildren().add(labelPlayedAccounts);
        vBox.getChildren().add(button);
        getChildren().add(vBox);
    }
}
