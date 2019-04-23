package nl.avans.sagrada.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.Main;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Toolcard;

public class ToolCardView extends CardView {
    private Toolcard toolcard;
    private PlayerController playerController;
    
    /**
     * Filled constructor
     * @param playerController PlayerController
     */
    public ToolCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
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
    
    /**
     * Generates a text to display a toolcard's description.
     */
    public void showDescription() {
        Text description = new Text(toolcard.getDescription());
        description.setFont(Main.SAGRADA_FONT);
        description.setTextAlignment(TextAlignment.CENTER);
        description.wrappingWidthProperty().set(getViewWidth());
        StackPane descriptionPane = new StackPane();
        descriptionPane.getChildren().add(description);
        descriptionPane.setAlignment(Pos.CENTER);
        descriptionPane.setPrefSize(getViewWidth(), (getViewHeight() / 3));
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
        imgview.setFitHeight((getViewHeight()/2));
        imgview.setFitWidth(getViewWidth() + 1);
        imgview.setPreserveRatio(false);
        imagePane.getChildren().add(imgview);
        imagePane.setPrefSize(getViewWidth(), (getViewHeight()/2));
        setCenter(imagePane);
    }
    
    /**
     * Generates a text to display the number at the top of the toolcard.
     */
    public void showNumber() {
        Text number = new Text(Integer.toString(toolcard.getId()) + "\n" + toolcard.getTitle());
        number.setFont(Main.SAGRADA_FONT);
        number.wrappingWidthProperty().set(getViewWidth());
        number.setTextAlignment(TextAlignment.CENTER);
        StackPane numberPane = new StackPane();
        numberPane.getChildren().add(number);
        numberPane.setPrefSize(getViewWidth(), (getViewHeight() / 6));
        setTop(numberPane);
    }
    
    @Override
    public void render() {
        getChildren().clear();
        showNumber();
        showImage(toolcard.getImageUrl());
        showDescription();
    }

    @Override
    public void render() {
        // TODO Auto-generated method stub
        
    }
}
