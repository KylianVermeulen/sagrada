package nl.avans.sagrada.model;

public class PublicObjectiveCard {
    private int id;
    private int seqnr;
    private String description;
    private String imagePath;
    
    /**
     * Filled constructor
     * @param id int
     * @param seqnr int
     * @param description String
     */
    public PublicObjectiveCard(int id, int seqnr, String description) {
        this.id = id;
        this.seqnr = seqnr;
        this.description = description;
    }

    /**
     * Returns public objective card id
     * @return id (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Sets public objective card id
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns public objective card sequence number
     * @return sequence number (int)
     */
    public int getSeqnr() {
        return seqnr;
    }

    /**
     * Sets the public objective cars sequence number
     * @param seqnr int
     */
    public void setSeqnr(int seqnr) {
        this.seqnr = seqnr;
    }

    /**
     * Returns the public objective card description
     * @return description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the public objective card description
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the public objective card image path
     * @return image path (String)
     */
    public String getImagePath() {
        int[] ids = new int[10];
        for (int index = 0; index < ids.length; index++) {
            if (getId() == (index + 1)) {
                setImagePath("/images/publicObjectiveCardImages/publicObjectiveCard" + (index + 1) + ".png");
            }
        }
        return imagePath;
    }

    /**
     * Sets the public objective card image path
     * @param imagePath String
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
