package nl.avans.sagrada.model;

public class Die {
    private int number;
    private String color;

    /**
     * Empty constructor
     */
    public Die() {
    }

    protected String getStringColor() {
        return this.color;
    }

    protected void setColor(String color) {
        this.color = color;
    }
}
