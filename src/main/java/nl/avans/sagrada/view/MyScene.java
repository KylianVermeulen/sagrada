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
        
        setRoot(regView); //alleen voor deze branch! zo kan deze window getest worden. 
                          //later moet dit niet naar regView gezet worden, maar naar een rootpane.
        
    }
    
//    public void setRootPane(RootPane rootPane) {
//        this.setRoot(rootPane);
//    }
    

}
