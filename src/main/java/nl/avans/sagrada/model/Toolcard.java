package nl.avans.sagrada.model;

import java.util.ArrayList;

import javafx.scene.image.Image;

public class Toolcard {
    private int id;
    private int seqnr;
    private String description;
    private String imageUrl;
    private String title;
    private ArrayList<FavorToken> favorTokens;
    
    /**
     * Empty constructor.
     */
    public Toolcard() {      
    }
    
    /**
     * Filled constructor.
     * @param id int
     * @param seqnr int
     * @param description String
     */
    public Toolcard(int id, int seqnr, String description) {
        this.id = id;
        this.seqnr = seqnr;
        this.description = description;
    }
    
    public void useToolcard() {
    }

    /**
     * Returns the toolcard id.
     * @return a toolcard id (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the toolcard id.
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the toolcard sequence number.
     * @return a toolcard sequence number (int)
     */
    public int getSeqnr() {
        return seqnr;
    }

    /**
     * Sets the sequence number for a toolcard
     * @param seqnr int
     */
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    /**
     * Returns the toolcard description.
     * @return a toolcard description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the toolcard description.
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the favortokens bound to the toolcard.
     * @return favortokens bound to this toolcard (ArrayList type: FavorToken)
     */
    public ArrayList<FavorToken> getFavorTokens() {
        return favorTokens;
    }

    /**
     * Binds favortokens to the toolcard.
     * @param favorTokens ArrayList type: FavorToken
     */
    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * Returns the image URL for the toolcard.
     * @return an image URL for this toolcard (String)
     */
    public String getImageUrl() {
        int[] ids = new int[12];
        for (int index = 0; index < ids.length; index++) {
            if (getId() == (index + 1)) {
                setImageUrl("/images/toolcardImages/toolcard" + (index + 1) + ".png");
            }
        }
        return imageUrl;
    }

    /**
     * Sets the image URL for the toolcard.
     * @param imageUrl String
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getTitle() {
        if (getId() == 1) {
            setTitle("Driepuntstang");
        }
        if (getId() == 2) {
            setTitle("Églomisé Borstel");
        }
        if (getId() == 3) {
            setTitle("Folie-aandrukker");
        }
        if (getId() == 4) {
            setTitle("Loodopenhaler");
        }
        if (getId() == 5) {
            setTitle("Rondsnijder");
        }
        if (getId() == 6) {
            setTitle("Fluxborstel");
        }
        if (getId() == 7) {
            setTitle("Loodhamer");
        }
        if (getId() == 8) {
            setTitle("Glasbreektang");
        }
        if (getId() == 9) {
            setTitle("Snijliniaal");
        }
        if (getId() == 10) {
            setTitle("Schuurblok");
        }
        if (getId() == 11) {
            setTitle("Fluxverwijderaar");
        }
        if (getId() == 12) {
            setTitle("Olieglassnijder");
        }
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
