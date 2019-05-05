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
     * Add a patterncardfield to the database.
     */
    public void addPatternCardField(PatternCardField patterncardField, PatternCard patterncard) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO patterncardfield (patterncard_idpatterncard, position_x, position_y, color, value) VALUES (?, ?, ?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, patterncard.getId()),
                    new QueryParameter(QueryParameter.INT, patterncardField.getxPos()),
                    new QueryParameter(QueryParameter.INT, patterncardField.getyPos()),
                    new QueryParameter(QueryParameter.STRING, patterncardField.getStringColor()),
                    new QueryParameter(QueryParameter.INT, patterncardField.getValue())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * FAKKA MOOIE METHODE
     */
    public void addPatternCardFields(ArrayList<PatternCardField> patternCardFields,
            PatternCard patternCard) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query(
                            "INSERT INTO patterncardfield VALUES (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?), (?, ?, ?, ?, ?)",
                            "update"),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(0).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(0).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(0).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(0).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(1).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(1).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(1).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(1).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(2).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(2).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(2).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(2).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(3).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(3).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(3).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(3).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(4).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(4).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(4).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(4).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(5).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(5).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(5).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(5).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(6).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(6).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(6).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(6).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(7).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(7).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(7).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(7).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(8).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(8).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(8).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(8).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(9).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(9).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(9).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(9).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(10).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(10).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(10).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(10).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(11).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(11).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(11).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(11).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(12).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(12).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(12).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(12).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(13).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(13).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(13).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(13).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(14).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(14).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(14).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(14).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(15).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(15).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(15).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(15).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(16).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(16).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(16).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(16).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(17).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(17).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(17).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(17).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(18).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(18).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(18).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(18).getValue()),
                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(19).getxPos()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(19).getyPos()),
                    new QueryParameter(QueryParameter.STRING,
                            patternCardFields.get(19).getStringColor()),
                    new QueryParameter(QueryParameter.INT, patternCardFields.get(19).getValue())
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
