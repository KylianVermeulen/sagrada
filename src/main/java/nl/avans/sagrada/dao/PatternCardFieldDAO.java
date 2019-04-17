package nl.avans.sagrada.dao;

import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;

public class PatternCardFieldDAO {
    private DBConnection dbConnection;
    
    public PatternCardFieldDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<PatternCardField> getPatterncardFieldsOfPatterncard() {
        return null;
    }

    public void addPatterncardField(PatternCardField patterncardField, PatternCard patterncard) {

    }
}
