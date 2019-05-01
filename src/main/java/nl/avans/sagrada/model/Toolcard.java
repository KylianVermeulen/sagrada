package nl.avans.sagrada.model;

import java.util.ArrayList;

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
    
    /**
     * Returns the title of the toolcard.
     * @return the title for this toolcard
     */
    public String getTitle() {
        int id = getId();
        String title;
        switch(id) {
            case 1:  title = "Driepuntstang";
                     break;
            case 2:  title = "Eglomise Borstel";
                     break;
            case 3:  title = "Folie-aandrukker";
                     break;
            case 4:  title = "Loodopenhaler";
                     break;
            case 5:  title = "Rondsnijder";
                     break;
            case 6:  title = "Fluxborstel";
                     break;
            case 7:  title = "Loodhamer";
                     break;
            case 8:  title = "Glasbreektang";
                     break;
            case 9:  title = "Snijliniaal";
                     break;
            case 10: title = "Schuurblok";
                     break;
            case 11: title = "Fluxverwijderaar";
                     break;
            case 12: title = "Olieglassnijder";
                     break;
            default: title = "Er is iets misgegaan";
                     break;            
        }
        return title;
    }
    
    /**
     * Sets the title for the toolcard.
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
