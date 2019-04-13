package nl.avans.sagrada;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static final int SCREEN_HEIGHT = 800;
	public static final int SCREEN_WIDTH = 1280;
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
		buildGui(primaryStage);
		primaryStage.show();
	}
	private void buildGui(Stage primaryStage) {
		rootScene = new MyScene();
		primaryStage.setScene(rootScene);
	}

}
