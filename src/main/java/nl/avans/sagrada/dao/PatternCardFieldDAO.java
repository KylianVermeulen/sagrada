package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;

public class PatternCardFieldDAO {
    private DBConnection dbConnection;

    public PatternCardFieldDAO() {
        dbConnection = new DBConnection();
    }

    public ArrayList<PatternCardField> getPatterncardFieldsOfPatterncard(PatternCard patternCard) {
        ArrayList<PatternCardField> list = new ArrayList<PatternCardField>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM patterncardfield WHERE patterncard_idpatterncard=?", "query"),
                    new QueryParameter(QueryParameter.INT, patternCard.getId())
            );
            while (rs.next()) {
                int xpos = rs.getInt("position_x");
                int ypos = rs.getInt("position_y");
                String color = rs.getString("color");
                int value = rs.getInt("value");
                PatternCardField patternCardField = new PatternCardField(xpos, ypos, color, value, patternCard);
                list.add(patternCardField);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addPatterncardField(PatternCardField patterncardField, PatternCard patterncard) {
    }
}
