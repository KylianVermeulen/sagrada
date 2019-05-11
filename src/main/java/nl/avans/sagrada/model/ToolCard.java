package nl.avans.sagrada.model;

import java.util.ArrayList;

public class ToolCard {
    private int id;
    private int seqnr;
    private String description;
    private String imagePath;
    private String name;
    private ArrayList<FavorToken> favorTokens;
    private boolean hasBeenPaidForBefore;

    /**
     * Empty constructor.
     */
    public ToolCard() {
    }

    /**
     * Filled constructor.
     *
     * @param id int
     * @param seqnr int
     * @param description String
     */
    public ToolCard(int id, String name, int seqnr, String description) {
        this.id = id;
        this.seqnr = seqnr;
        this.description = description;
        this.name = name;
        favorTokens = new ArrayList<>();
    }

    public void useToolCard() {}

    /**
     * Returns the toolcard id.
     *
     * @return a toolcard id (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the toolcard id.
     *
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the toolcard sequence number.
     *
     * @return a toolcard sequence number (int)
     */
    public int getSeqnr() {
        return seqnr;
    }

    /**
     * Sets the sequence number for a toolcard
     *
     * @param seqnr int
     */
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    /**
     * Returns the toolcard description.
     *
     * @return a toolcard description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the toolcard description.
     *
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the favortokens bound to the toolcard.
     *
     * @return favortokens bound to this toolcard (ArrayList type: FavorToken)
     */
    public ArrayList<FavorToken> getFavorTokens() {
        return favorTokens;
    }

    /**
     * Binds favortokens to the toolcard.
     *
     * @param favorTokens ArrayList type: FavorToken
     */
    public void setFavorTokens(ArrayList<FavorToken> favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * Returns the image path for the toolcard.
     *
     * @return an image path for this toolcard (String)
     */
    public String getImagePath() {
        int[] ids = new int[12];
        for (int index = 0; index < ids.length; index++) {
            if (getId() == (index + 1)) {
                setImagePath("/images/toolcardImages/toolcard" + (index + 1) + ".png");
            }
        }
        return imagePath;
    }

    /**
     * Sets the image path for the toolcard.
     *
     * @param imagePath String
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Returns the name of the toolcard.
     *
     * @return the name for this toolcard
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for the toolcard.
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the payment status for the toolcard. If true, this toolcard has already been paid for
     * before.
     * 
     * @return the payment status for the toolcard
     */
    public boolean hasBeenPaidForBefore() {
        return hasBeenPaidForBefore;
    }

    /**
     * Sets the payment status for the toolcard.
     * 
     * @param hasBeenPaidForBefore boolean
     */
    public void setHasBeenPaidForBefore(boolean hasBeenPaidForBefore) {
        this.hasBeenPaidForBefore = hasBeenPaidForBefore;
    }
    
    public void addFavorToken(FavorToken token) {
        favorTokens.add(token);
    }
}
