package nl.avans.sagrada.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MyScene extends Scene {

    //private RootPane rootPane;
    
    public MyScene() {
        super(new Pane());
        //rootPane = new RootPane();
        RegisterView regView = new RegisterView();
        //setRoot(rootPane);
        setRoot(regView);
        //add login screen as standard "fill" for rootpane.
    }
    
//    public void setRootPane(RootPane rootPane) {
//        this.setRoot(rootPane);
//    }
    

}
