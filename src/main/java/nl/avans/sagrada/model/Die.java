package nl.avans.sagrada.model;

public class Die {
    private int number;
    private String color;

    /**
     * Empty constructor
     */
    public Die() {
    }

    /**
     * Returns the Color as a String
     *
     * @return String
     */
    protected String getStringColor() {
        return this.color;
    }

    /**
     * Sets the Color
     *
     * @param color String
     */
    protected void setColor(String color) {
        this.color = color;
    }
}
