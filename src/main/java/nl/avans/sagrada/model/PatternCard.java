package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.PatterncardDAO;

public class PatternCard {
    private int id;
    private int difficult;
    private boolean standard;

    private PatternCardField[] patterncardFields;

    public PatternCard() {
    }

    public PatternCard(int id, int difficult, boolean standard) {
        this.id = id;
        this.difficult = difficult;
        this.standard = standard;
    }

    public void save() {
        PatterncardDAO patterncardDAO = new PatterncardDAO();
        patterncardDAO.createPatterncard(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDifficult() {
        return difficult;
    }

    public void setDifficult(int difficult) {
        this.difficult = difficult;
    }

    public boolean isStandard() {
        return standard;
    }

    public void setStandard(boolean standard) {
        this.standard = standard;
    }

    public PatternCardField[] getPatterncardFields() {
        return patterncardFields;
    }

    public void setPatterncardFields(PatternCardField[] patterncardFields) {
        this.patterncardFields = patterncardFields;
    }

    public PatternCardField getBestLocationForDice(GameDie die) {
        return null;
    }

    public void generateRandomCard() {
    }
}
