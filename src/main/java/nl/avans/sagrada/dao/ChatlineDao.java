package nl.avans.sagrada.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import nl.avans.sagrada.database.DBConnection;
import nl.avans.sagrada.database.Query;
import nl.avans.sagrada.database.QueryParameter;
import nl.avans.sagrada.model.Chatline;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.Player;

public class ChatlineDao {
	private DBConnection dbConnection;

	/**
	 * Constructor
	 */
	public ChatlineDao() {
		dbConnection = new DBConnection();
	}

	/**
	 * Method to add chatline to database
	 *
	 * @param chatline Chatline
	 */
	public void addChatline(Chatline chatline) {
		Player player = chatline.getPlayer();
		Timestamp timeStamp = chatline.getTimestamp();
		String message = chatline.getMessage();
		try {
			ResultSet rs = dbConnection.executeQuery(
					new Query("INSERT INTO chatline(player_idplayer, time, message) VALUES (?, ?, ?)", "update"),
					new QueryParameter(QueryParameter.INT, player.getId()),
					new QueryParameter(QueryParameter.TIMESTAMP, timeStamp),
					new QueryParameter(QueryParameter.STRING, message));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get all chatlines of a game
	 * 
	 * @param game Game
	 * @return
	 */
	public ArrayList<Chatline> getChatlinesOfGame(Game game) {
		ArrayList<Chatline> chatlines = new ArrayList<>();
		try {
			ResultSet rs = dbConnection.executeQuery(new Query(
					"SELECT * FROM chatline JOIN player ON chatline.player_idplayer = player.idplayer WHERE game_idgame =?",
					"query"), new QueryParameter(QueryParameter.INT, game.getId()));

			while (rs.next()) {
				Chatline chatline = new Chatline();
				chatline.setPlayer(new PlayerDao().getPlayerById(rs.getInt("player_idplayer")));
				chatline.setTimestamp(rs.getTimestamp("time"));
				chatline.setMessage(rs.getString("message"));
				chatlines.add(chatline);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return chatlines;
	}

	/**
	 * Method to set the current database time to a chatline
	 *
	 * @param chatline Chatline
	 */
	public void getTime(Chatline chatline) {
		try {
			ResultSet rs = dbConnection.executeQuery(new Query("SELECT NOW()", "query"));

			while (rs.next()) {
				chatline.setTimestamp(rs.getTimestamp("NOW()"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to check if a player has already send a message on 1 specific time
	 *
	 * @param chatline Chatline
	 */
	public boolean timeExistsOfPlayer(Chatline chatline) {
		try {
			ResultSet rs = dbConnection.executeQuery(
					new Query("SELECT count(*) FROM chatline WHERE time =? AND player_idplayer =?", "query"),
					new QueryParameter(QueryParameter.TIMESTAMP, chatline.getTimestamp()),
					new QueryParameter(QueryParameter.INT, chatline.getPlayer().getId()));
			if (rs.next()) {
				int count = rs.getInt("count(*)");
				if (count > 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}