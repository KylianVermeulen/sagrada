package nl.avans.sagrada.model;

public class PublicObjectiveCard {
    private int id;
    private String name;
    private String description;
    private int points;
    private String imagePath;

    public PublicObjectiveCard() {

    }

    /**
     * Filled constructor
     * 
     * @param id int
     * @param seqnr int
     * @param description String
     */
    public PublicObjectiveCard(int id, String name, String description, int points) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.points = points;
    }

    /**
     * Returns public objective card id
     * 
     * @return id (int)
     */
    public int getId() {
        return id;
    }

    /**
     * Sets public objective card id
     * 
     * @param id int
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the public objective card description
     * 
     * @return description (String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the public objective card description
     * 
     * @param description String
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Returns the public objective card image path
     * 
     * @return image path (String)
     */
    public String getImagePath() {
        int[] ids = new int[10];
        for (int index = 0; index < ids.length; index++) {
            if (getId() == (index + 1)) {
                setImagePath("/images/publicObjectiveCardImages/publicObjectiveCard" + (index + 1)
                        + ".png");
            }
        }
        return imagePath;
    }

    /**
     * Sets the public objective card image path
     * 
     * @param imagePath String
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
