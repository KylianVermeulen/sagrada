package nl.avans.sagrada.dao;

import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Die;
import nl.avans.sagrada.model.GameDie;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GameDieDao {
    private DBConnection dbConnection;

    /**
     * Constructor, Initializes DBConnection
     */
    public GameDieDao() {
        dbConnection = new DBConnection();
    }

    public void addDie(int gameId, GameDie gameDie) {
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("INSERT INTO gamedie (idgame, dienumber, diecolor, eyes) VALUES (?, ?, ?, ?)", "update"),
                    new QueryParameter(QueryParameter.INT, gameId),
                    new QueryParameter(QueryParameter.INT, gameDie.getNumber()),
                    new QueryParameter(QueryParameter.STRING, gameDie.getColor()),
                    new QueryParameter(QueryParameter.INT, gameDie.getEyes())
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<GameDie> getDice(int gameId) {
        ArrayList<GameDie> gameDice = new ArrayList<GameDie>();
        try {
            ResultSet rs = dbConnection.executeQuery(
                    new Query("SELECT * FROM gamedie WHERE gamedie_idgame=?", "query"),
                    new QueryParameter(QueryParameter.INT, gameId));
            while (rs.next()) {
                GameDie gameDie = new GameDie(rs.getInt("number"), rs.getString("color"), rs.getInt("eyes"));
                gameDice.add(gameDie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gameDice;
    }


//    public void addPatternCardFields(ArrayList<PatternCardField> patternCardFields, PatternCard patternCard) {
//        List<QueryParameter[]> queryParametersList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) { // 20 fields
//            QueryParameter[] queryParameters = {
//                    new QueryParameter(QueryParameter.INT, patternCard.getId()),
//                    new QueryParameter(QueryParameter.INT, patternCardFields.get(i).getxPos()),
//                    new QueryParameter(QueryParameter.INT, patternCardFields.get(i).getyPos()),
//                    new QueryParameter(QueryParameter.STRING,
//                            patternCardFields.get(i).getFXColor()),
//                    new QueryParameter(QueryParameter.INT, patternCardFields.get(i).getValue())
//            };
//            queryParametersList.add(queryParameters);
//        }
//        try {
//            int[] ints = dbConnection.executeBatchQuery(
//                    new Query("INSERT INTO patterncardfield VALUES (?, ?, ?, ?, ?)", "update",
//                            queryParametersList));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    public ArrayList<GameDie> getDice(int gameId) {
//        ArrayList<GameDie> gameDice = new ArrayList<>();
//        for (Die die : new DieDao().getDice()) {
//            GameDie gameDie = new GameDie(die, 0);
//            gameDice.add(gameDie);
//            try {
//                ResultSet rs =
//                        dbConnection.executeQuery(
//                                new Query("INSERT INTO gamedie (idgame, dienumber, diecolor, eyes) VALUES (?, ?, ?, ?)",
//                                        "update"),
//                                new QueryParameter(QueryParameter.INT, gameId),
//                                new QueryParameter(QueryParameter.INT, die.getNumber()),
//                                new QueryParameter(QueryParameter.STRING, die.getColor()),
//                                new QueryParameter(QueryParameter.INT, gameDie.getEyes())
//                        );
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return gameDice;
//    }
}
