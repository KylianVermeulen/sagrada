package nl.avans.sagrada;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import nl.avans.sagrada.view.MyScene;

public class Main extends Application {
    public static final int SCREEN_HEIGHT = 840;
    public static final int SCREEN_WIDTH = 1280;
    public static final Font SAGRADA_FONT = new Font("Segoe Script", 8);
    private MyScene rootScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setHeight(SCREEN_HEIGHT);
        primaryStage.setWidth(SCREEN_WIDTH);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Sagrada groep P");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/icon.png")));
        buildGui(primaryStage);
        primaryStage.show();
    }

    private void buildGui(Stage primaryStage) {
        rootScene = new MyScene();
        primaryStage.setScene(rootScene);
    }
}
