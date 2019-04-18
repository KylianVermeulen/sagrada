package nl.avans.sagrada.view.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Alert extends Popup {
    private String title;
    private String message;
    private String type;

    public static final int WIDTH_ALERT = 320;
    public static final int HEIGHT_ALERT = 90;

    /**
     * Full constructor for a alert
     *
     * @param title String
     * @param message String
     * @param type String (info, succes, warning, error)
     */
    public Alert(String title, String message, String type) {
        super(WIDTH_ALERT, HEIGHT_ALERT);
        this.title = title;
        this.message = message;
        this.type = type;

        // USE_PREF_SIZE is a javafx const. Pref size is set in Popup class though super()
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground();
        render();
    }

    /**
     * Renders the view with all the information
     */
    @Override
    public void render() {
        getChildren().clear();
        VBox vBox = new VBox();
        vBox.setPrefSize(super.getwidth(), super.getheight());
        vBox.setSpacing(3);
        vBox.setPadding(new Insets(20));
        vBox.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label(title);
        titleLabel.setTextFill(Color.web("#fff"));
        titleLabel.setFont(Font.font("sans-serif", FontWeight.BOLD, 14));
        Label messageLabel = new Label(message);
        messageLabel.setTextFill(Color.web("#fff"));
        messageLabel.setFont(Font.font("sans-serif", FontWeight.NORMAL, 13));
        vBox.getChildren().add(titleLabel);
        vBox.getChildren().add(messageLabel);
        getChildren().add(vBox);
    }

    public void setBackground() {
        switch (type) {
            case "info":
                setBackground(new Background(new BackgroundFill(Color.web("#66c3d6"), new CornerRadii(26), null)));
                break;
            case "succes":
                setBackground(new Background(new BackgroundFill(Color.web("#40bc8a"), new CornerRadii(26), null)));
                break;
            case "warning":
                setBackground(new Background(new BackgroundFill(Color.web("#fcb738"), new CornerRadii(26), null)));
                break;
            case "error":
                setBackground(new Background(new BackgroundFill(Color.web("#ff5955"), new CornerRadii(26), null)));
                break;
            default:
                setBackground(new Background(new BackgroundFill(Color.web("#66c3d6"), new CornerRadii(26), null)));
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
