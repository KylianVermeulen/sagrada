package nl.avans.sagrada.model;

public class Die {
    private int number;
    private String color;

    /**
     * Empty constructor.
     */
    public Die() {
    }

    /**
     * Full constructor, initializes all instance variables.
     *
     * @param number The number of this die.
     * @param color The color of this die.
     */
    public Die(int number, String color) {
        this.number = number;
        this.color = color;
    }

    /**
     * This method will return the number of this die.
     *
     * @return The number of this die.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Registers the number is this die. The number is a unique identifier for this die.
     *
     * @param number The number of this die.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * This method will return the color of this die. The color is one of the following: rood;
     * groen; geel; paars; blauw.
     *
     * @return The color of this die.
     */
    public String getColor() {
        return color;
    }

    /**
     * Registers the color of this die. The color must be one of the following: rood; groen; geel;
     * paars; blauw;
     *
     * @param color The color of this die.
     */
    public void setColor(String color) {
        this.color = color;
    }
}
