package nl.avans.sagrada.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Toolcard;

public class ToolCardView extends CardView {
    private Toolcard toolcard;
    private PlayerController playerController;
    private Game game;
    private int toolCardSelection;
    private boolean isZoomed;
    private static final Color CARDSANDYELLOW = Color.rgb(255, 240, 173);
    
    /**
     * Filled constructor
     * @param playerController PlayerController
     */
    public ToolCardView(PlayerController playerController, Game game) {
        super();
        setBackground(new Background(new BackgroundFill(CARDSANDYELLOW, null, null)));
        this.playerController = playerController;
        this.game = game;
        isZoomed = false;
    }
    
    public boolean isZoomed() {
        return isZoomed;
    }

    public void setZoomed(boolean isZoomed) {
        this.isZoomed = isZoomed;
    }

    /**
     * Returns the toolcard that is currently linked to this view.
     * @return toolcard that is linked to this view
     */
    public Toolcard getToolCard() {
        return toolcard;
    }

    /**
     * Sets the current toolcard that is linked to this view.
     * @param toolcard Toolcard
     */
    public void setToolCard(Toolcard toolcard) {
        this.toolcard = toolcard;
    }
    
    public void setToolCardSelection(int selection) {
        toolCardSelection = selection;
    }
    
    public int getToolCardSelection() {
        return toolCardSelection;
    }

    /**
     * Generates a text to display a toolcard's description.
     */
    public void showDescription() {
        Text description = new Text(toolcard.getDescription());
        StackPane descriptionPane = new StackPane();
        if (isZoomed) {
            description.setFont(Main.SAGRADA_ZOOM_FONT);
            description.wrappingWidthProperty().set(getZoomViewWidth());
            descriptionPane.setPrefSize(CardView.ZOOM_CARD_WIDTH, (CardView.ZOOM_CARD_HEIGHT / 3));
        } else {
            description.setFont(Main.SAGRADA_FONT); 
            description.wrappingWidthProperty().set(getViewWidth());
            descriptionPane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT / 3));
        }
        description.setTextAlignment(TextAlignment.CENTER);
        descriptionPane.getChildren().add(description);
        descriptionPane.setAlignment(Pos.CENTER);
        setBottom(descriptionPane);
    }
    
    /**
     * Displays the toolcard image.
     * @param url String
     */
    public void showImage(String url) {
        Image image = new Image(url);
        StackPane imagePane = new StackPane();
        ImageView imgview = new ImageView(image);
        if (isZoomed) {
            imgview.setFitHeight((CardView.ZOOM_CARD_HEIGHT/2));
            imgview.setFitWidth(CardView.ZOOM_CARD_WIDTH + 1);
            imagePane.setPrefSize(CardView.ZOOM_CARD_WIDTH, (CardView.ZOOM_CARD_HEIGHT/2));  
        } else {
            imgview.setFitHeight((CardView.CARD_HEIGHT/2));
            imgview.setFitWidth(CardView.CARD_WIDTH + 1);
            imagePane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT/2));
        }
        imgview.setPreserveRatio(false);
        imagePane.getChildren().add(imgview);
        setCenter(imagePane);
    }
    
    /**
     * Generates a text to display the number at the top of the toolcard.
     */
    public void showTop() {
        Text number = new Text(Integer.toString(toolcard.getSeqnr()) + "\n" + toolcard.getTitle());
        StackPane numberPane = new StackPane();
        if (isZoomed) {
            number.setFont(Main.SAGRADA_ZOOM_FONT);
            number.wrappingWidthProperty().set(CardView.ZOOM_CARD_WIDTH);
            numberPane.setPrefSize(CardView.ZOOM_CARD_WIDTH, (CardView.ZOOM_CARD_HEIGHT / 6));
            Button backButton = new Button("Terug");
            backButton.setOnAction(e -> playerController.viewToolcards(game));
            numberPane.getChildren().add(number);
            numberPane.getChildren().add(backButton);
            StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        } else {
            number.setFont(Main.SAGRADA_FONT);
            number.wrappingWidthProperty().set(CardView.CARD_WIDTH);
            numberPane.setPrefSize(CardView.CARD_WIDTH, (CardView.CARD_HEIGHT / 6));
            numberPane.getChildren().add(number);
        }
        number.setTextAlignment(TextAlignment.CENTER);
        setTop(numberPane);
    }
    
    @Override
    public void render() {
        getChildren().clear();
        showTop();
        showImage(toolcard.getImageUrl());
        showDescription();
    }

}
