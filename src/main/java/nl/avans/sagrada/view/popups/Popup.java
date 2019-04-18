package nl.avans.sagrada.view.popups;

import javafx.scene.layout.Pane;
import nl.avans.sagrada.view.interfaces.ViewInterface;

public abstract class Popup extends Pane implements ViewInterface {
    private int posX;
    private int posY;
    private int width;
    private int height;

    /**
     * Full constructor for a popup view
     *
     * @param posX int
     * @param posY int
     * @param width int
     * @param height int
     */
    public Popup(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        setPrefSize(width, height);
        setLayoutX(posX);
        setLayoutY(posY);
    }

    /**
     * Partial constructor for a popup view
     *
     * @param width int
     * @param height int
     */
    public Popup(int width, int height) {
        this.width = width;
        this.height = height;
        setPrefSize(width, height);
    }

    /**
     * Renders the view with all the information
     */
    @Override
    public abstract void render();

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getwidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getheight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
