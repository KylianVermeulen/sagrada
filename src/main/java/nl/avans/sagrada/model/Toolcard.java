package nl.avans.sagrada.model;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Toolcard {
    private int id;
    private int seqnr;
    private String description;
    private Image image;
    private ArrayList<FavorToken> favorTokens;
    
    public Toolcard() {      
    }
    
    public Toolcard(int id, int seqnr, String description, Image image) {
        this.id = id;
        this.seqnr = seqnr;
        this.description = description;
        this.image = image;
    }
    
    public void useToolcard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeqnr() {
        return seqnr;
    }

    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<FavorToken> getFavorTokens() {
        return favorTokens;
    }

    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
}
