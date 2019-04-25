package nl.avans.sagrada.model;

public class Die {
    private int number;
    private String color;

    public Die() {
    }

    public Die(int number, String color) {
        this.number = number;
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
