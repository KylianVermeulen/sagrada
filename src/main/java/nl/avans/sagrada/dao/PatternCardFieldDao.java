package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;

public class PatternCardFieldDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public PatternCardFieldDao() {
        dbConnection = new DBConnection();
    }

    public ArrayList<PatternCardField> getPatternCardFieldsOfPatterncard(PatternCard patternCard) {
        ArrayList<PatternCardField> list = new ArrayList<>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "SELECT * FROM sagrada.patterncardfield WHERE patterncard_idpatterncard=? ORDER BY position_x, position_y",
                            "query"),
                    new QueryParameter(QueryParameter.INT, patternCard.getId())
            );
            while (rs.next()) {
                int xpos = rs.getInt("position_x");
                int ypos = rs.getInt("position_y");
                String color = rs.getString("color");
                int value = rs.getInt("value");
                PatternCardField patternCardField = new PatternCardField(xpos, ypos, color, value,
                        patternCard);
                list.add(patternCardField);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
