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
    
    
    public ToolCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
        //setImageUrl("/images/toolcard1.png");
    }

    public void setToolCard(Toolcard toolcard) {
        this.toolcard = toolcard;
    }
    
    public void showDescription() {
        Text description = new Text(toolcard.getDescription());
        description.setFont(Main.SAGRADAFONT);
        description.setTextAlignment(TextAlignment.CENTER);
        StackPane descriptionPane = new StackPane();
        descriptionPane.getChildren().add(description);
        descriptionPane.setAlignment(Pos.CENTER);
        descriptionPane.setPrefSize(getViewWidth(), (getViewHeight() / 3));
        setBottom(descriptionPane);
    }
    
    public void showImage(String url) {
        url = "/images/toolcard1.png";
        Image image = new Image(url);
        StackPane imagePane = new StackPane();
        ImageView imgview = new ImageView(image);
        imgview.setFitHeight((getViewHeight()/2));
        imgview.setFitWidth(getViewWidth() + 1);
        imgview.setPreserveRatio(false);
        imagePane.getChildren().add(imgview);
        imagePane.setPrefSize(getViewWidth(), (getViewHeight()/2));
        setCenter(imagePane);
        System.out.println(url);
    }
    
    public void showNumber() {
        Text number = new Text(Integer.toString(toolcard.getId()));
        number.setFont(Main.SAGRADAFONT);
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

}
