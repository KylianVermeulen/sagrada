package nl.avans.sagrada.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.ToolCard;

public class ToolCardView extends CardView {
    private ToolCard toolCard;
    private PlayerController playerController;

    /**
     * Filled constructor
     *
     * @param playerController PlayerController
     */
    public ToolCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
        String css = this.getClass().getResource("/css/style.css").toExternalForm();
        getStylesheets().add(css);
        setId("toolcardBackground");
    }

    /**
     * Returns the toolCard that is currently linked to this view.
     *
     * @return toolCard that is linked to this view
     */
    public ToolCard getToolCard() {
        return toolCard;
    }

    /**
     * Sets the current toolCard that is linked to this view.
     *
     * @param toolCard Toolcard
     */
    public void setToolCard(ToolCard toolCard) {
        this.toolCard = toolCard;
    }

    /**
     * Generates a text to display a toolCard's description.
     */
    public void showDescription() {
        Text description = new Text(toolCard.getDescription());
        description.setFont(Main.SAGRADA_FONT);
        description.setTextAlignment(TextAlignment.CENTER);
        description.wrappingWidthProperty().set(CardView.CARD_WIDTH);
        StackPane descriptionPane = new StackPane();
        descriptionPane.getChildren().add(description);
        descriptionPane.setAlignment(Pos.CENTER);
        descriptionPane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT / 3));
        setBottom(descriptionPane);
    }

    /**
     * Displays the toolCard image.
     *
     * @param url String
     */
    public void showImage(String url) {
        Image image = new Image(url);
        StackPane imagePane = new StackPane();
        ImageView imgview = new ImageView(image);
        imgview.setFitHeight((CardView.CARD_HEIGHT / 2));
        imgview.setFitWidth(CardView.CARD_WIDTH + 1);
        imgview.setPreserveRatio(false);
        imagePane.getChildren().add(imgview);
        imagePane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT / 2));
        setCenter(imagePane);
    }

    /**
     * Generates a text to display the number at the top of the toolCard.
     */
    public void showNumber() {
        Text name = new Text(Integer.toString(toolCard.getSeqnr()) + "\n" + toolCard.getName());
        name.setFont(Main.SAGRADA_FONT);
        name.wrappingWidthProperty().set(CardView.CARD_WIDTH);
        name.setTextAlignment(TextAlignment.CENTER);
        StackPane numberPane = new StackPane();
        numberPane.getChildren().add(name);
        numberPane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT / 6));
        setTop(numberPane);
    }

    @Override
    public void render() {
        getChildren().clear();
        showNumber();
        showImage(toolCard.getImagePath());
        showDescription();
    }
}
