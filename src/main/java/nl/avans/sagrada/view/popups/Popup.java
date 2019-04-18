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

    /**
     * Get x position of view
     *
     * @return int
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Set x position to view
     *
     * @param posX int
     */
    public void setPosX(int posX) {
        this.posX = posX;
    }

    /**
     * Get y position of view
     *
     * @return int
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Set y position to view
     *
     * @param posY int
     */
    public void setPosY(int posY) {
        this.posY = posY;
    }

    /**
     * Get width of view
     * Not camelcase because of Pane class extend, method is final
     *
     * @return int
     */
    public int getwidth() {
        return width;
    }

    /**
     * Set width to view
     *
     *
     * @param width int
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get height of view
     * Not camelcase because of Pane class extend, method is final
     *
     * @return int
     */
    public int getheight() {
        return height;
    }

    /**
     * Set height of view
     *
     * @param height int
     */
    public void setHeight(int height) {
        this.height = height;
    }
}
