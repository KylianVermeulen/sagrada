package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
                            "SELECT * FROM patterncardfield WHERE patterncard_idpatterncard=? ORDER BY position_x, position_y",
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

    /**
     * Add all patterncardfields to the database using batch query.
     */
    public void addPatternCardFields(ArrayList<PatternCardField> patternCardFields,
            PatternCard patternCard) {
        List<QueryParameter[]> queryParametersList = new ArrayList<>();
        for (int i = 0; i < 20; i++) { // 20 fields
            QueryParameter[] queryParameters = {
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(i).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(i).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(i).getColor()),
                    new QueryParameter(QueryParameter.INT, (patternCardFields.get(i).hasValue() ? patternCardFields.get(i).getValue() : null))
            };
            queryParametersList.add(queryParameters);
        }
        try {
            int[] ints = dbConnection.executeBatchQuery(
                    new Query("INSERT INTO patterncardfield VALUES (?, ?, ?, ?, ?)", "update",
                            queryParametersList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
