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
    public static final int WIDTH_ALERT = 320;
    public static final int HEIGHT_ALERT = 90;
    private String title;
    private String message;
    private AlertType type;

    /**
     * Full constructor for a alert
     *
     * @param title String
     * @param message String
     * @param type AlertType
     */
    public Alert(String title, String message, AlertType type) {
        super(WIDTH_ALERT, HEIGHT_ALERT);
        this.title = title;
        this.message = message;
        this.type = type;

        // USE_PREF_SIZE is a javafx const. Pref size is set in Popup class though super()
        // Max size is needed because a StackPane will resize children to own size.
        setMaxSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setBackground();
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

    /**
     * Set the background color based upon String type
     */
    public void setBackground() {
        switch (type) {
            case INFO:
                setBackground(new Background(
                        new BackgroundFill(Color.web("#66c3d6"), new CornerRadii(26), null)));
                break;
            case SUCCES:
                setBackground(new Background(
                        new BackgroundFill(Color.web("#40bc8a"), new CornerRadii(26), null)));
                break;
            case WARNING:
                setBackground(new Background(
                        new BackgroundFill(Color.web("#fcb738"), new CornerRadii(26), null)));
                break;
            case ERROR:
                setBackground(new Background(
                        new BackgroundFill(Color.web("#ff5955"), new CornerRadii(26), null)));
                break;
            default:
                setBackground(new Background(
                        new BackgroundFill(Color.web("#66c3d6"), new CornerRadii(26), null)));
        }
    }

    /**
     * Get title of alert
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title to alert
     *
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get message of alert
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message to alert
     *
     * @param message String
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get type of alert
     *
     * @return String
     */
    public AlertType getType() {
        return type;
    }

    /**
     * Set type to alert
     *
     * @param type String
     */
    public void setType(AlertType type) {
        this.type = type;
    }
}
