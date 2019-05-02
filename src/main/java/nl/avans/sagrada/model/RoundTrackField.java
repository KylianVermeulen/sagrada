package nl.avans.sagrada.model;

import java.util.ArrayList;

public class RoundTrackField {
    private GameDie[] gameDies;

    /**
     * Full Constructor
     */
    public RoundTrackField() {
        gameDies = new GameDie[10];
    }

    /**
     * Adds a die to the array
     *
     * @param gameDie GameDie
     */
    public void addDie(GameDie gameDie) {
        for (int i = 0; i < gameDies.length; i++) {
            if (gameDies[i] == null) {
                gameDies[i] = gameDie;
                return;
            }
        }
    }

    /**
     * Gets the gameDies
     *
     * @return GameDie[]
     */
    public GameDie[] getGameDies() {
        return this.gameDies;
    }
}
