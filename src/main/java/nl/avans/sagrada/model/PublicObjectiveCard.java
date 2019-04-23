package nl.avans.sagrada.model;

public class PublicObjectiveCard {
    private int id;
    private int seqnr;
    private String description;
    private String imageUrl;
    
    public PublicObjectiveCard(int id, int seqnr, String description) {
        this.id = id;
        this.seqnr = seqnr;
        this.description = description;
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

    public String getImageUrl() {
        int[] ids = new int[10];
        for (int index = 0; index < ids.length; index++) {
            if (getId() == (index + 1)) {
                setImageUrl("/images/publicObjectiveCardImages/publicObjectiveCard" + (index + 1) + ".png");
            }
        }
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
