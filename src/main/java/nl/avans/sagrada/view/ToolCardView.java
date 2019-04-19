package nl.avans.sagrada.view;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Toolcard;

public class ToolCardView extends CardView {
    private Toolcard toolcard;
    private PlayerController playerController;
    private String imageUrl;
    
    public ToolCardView(PlayerController playerController) {
        super();
        this.playerController = playerController;
        setImageUrl("File:/images/catPic.jpg");
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setToolCard(Toolcard toolcard) {
        this.toolcard = toolcard;
    }
    
    public void showDescription() {
        Text description = new Text("Description (achtergrond is tijdelijk als test!)");
        description.setFont(new Font("Arial", 14));
        description.setTextAlignment(TextAlignment.CENTER);
        StackPane descriptionPane = new StackPane();
        descriptionPane.setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
        descriptionPane.getChildren().add(description);
        descriptionPane.setAlignment(Pos.CENTER);
        descriptionPane.setPrefSize(getViewWidth(), (getViewHeight() / 3));
        setBottom(descriptionPane);
    }
    
    public void showImage(String url) {
        Image image = new Image(url);
        Pane imagePane = new Pane();
        imagePane.getChildren().add(new ImageView(image));
        setCenter(imagePane);
    }
    
//    public void showSeqNumber() {
//        Text seqNumber = new Text(Integer.toString(toolcard.getSeqnr()));
//        seqNumber.setFont(new Font("Arial", 20));
//        Pane seqNumberPane = new Pane();
//        seqNumberPane.getChildren().add(seqNumber);
//        setTop(seqNumberPane);
//    }
    
    @Override
    public void render() {
        getChildren().clear();
        //showSeqNumber();
        showImage(getImageUrl());
        showDescription();
    }

}
