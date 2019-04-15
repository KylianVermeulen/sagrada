package nl.avans.sagrada.model;

import nl.avans.sagrada.dao.PatterncardDAO;

public class PatternCard {
    private int id;
    private int difficult;
    private boolean standard;

    private PatternCardField[] patterncardFields;

    public void save() {
        PatterncardDAO patterncardDAO = new PatterncardDAO();
        patterncardDAO.createPatterncard(this);
    }

    public PatternCardField getBestLocationForDice(GameDie die) {
        return null;
    }

    public void generateRandomCard() {
    }
}
