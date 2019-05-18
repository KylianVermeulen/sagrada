package nl.avans.sagrada.view;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.toolcard.ToolCard;
import nl.avans.sagrada.model.FavorToken;
import nl.avans.sagrada.model.Game;

public class ToolCardView extends CardView {
    private ToolCard toolCard;
    private PlayerController playerController;
    private TilePane tokenPane;
    
    private final int TOKEN_PANE_WIDTH = 45;
    private final int TOKEN_PANE_HEIGHT = 10;
    private final int TOKEN_PANE_GAP = 1;
    private final double TOKEN_SIZE = 3.5;

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
        setOnMouseClicked(event -> {
            MouseEvent clickEvent = (MouseEvent) event;
            playerController.setActiveToolCard(toolCard);
            playerController.actionChangeDie(event);
        });
        tokenPane = new TilePane();
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
        numberPane.setAlignment(tokenPane, Pos.TOP_RIGHT);
        numberPane.getChildren().addAll(name, tokenPane);
        numberPane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT / 6));
        setTop(numberPane);
    }
    
    /**
     * Generates a pane that will contain the favortokens
     */
    public void showTokenPane() {
        tokenPane.setMaxHeight(TOKEN_PANE_HEIGHT);
        tokenPane.setMinHeight(TOKEN_PANE_HEIGHT);
        tokenPane.setMaxWidth(TOKEN_PANE_WIDTH);
        tokenPane.setMinWidth(TOKEN_PANE_WIDTH);
        tokenPane.setVgap(TOKEN_PANE_GAP);
        tokenPane.setHgap(TOKEN_PANE_GAP);
    }

    @Override
    public void render() {
        getChildren().clear();
        showTokenPane();
        showNumber();
        showImage(toolCard.getImagePath());
        showDescription();
        setOnMouseClicked(e -> playerController.actionPayForToolCard(toolCard, this));
    }
    
    /**
     * Method to add a favortoken to the tokenpane
     * @param color
     */
    public void addFavorToken(Color color) {
        Circle favorToken = new Circle(TOKEN_SIZE, color);
        tokenPane.getChildren().add(favorToken);
    }
    
    /**
     * Method to add all the paid favortokens to the tokenpane
     * @param favorTokens
     */
    public void setFavorTokens(ArrayList<FavorToken> favorTokens, Game game) {
        for(int i = 0; i < game.getPlayers().size(); i++){
            for(int e = 0; e < favorTokens.size(); e++) {
                if(favorTokens.get(e).getPlayer().getId() == game.getPlayers().get(i).getId()){
                    addFavorToken(game.getPlayers().get(i).getPlayerColor());
                }
            }

        }

    }
}
