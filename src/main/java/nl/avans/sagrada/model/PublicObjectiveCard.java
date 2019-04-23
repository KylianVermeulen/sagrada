package nl.avans.sagrada.model;

public class PublicObjectiveCard {
    private int id;
    private int seqnr;
    private String description;
    private int scorePoints;
    
    public PublicObjectiveCard(int id, int seqnr, String description, int scorePoints) {
        this.id = id;
        this.seqnr = seqnr;
        this.description = description;
        this.scorePoints = scorePoints;
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

    public int getScorePoints() {
        return scorePoints;
    }

    public void setScorePoints(int scorePoints) {
        this.scorePoints = scorePoints;
    }
    
    
}
