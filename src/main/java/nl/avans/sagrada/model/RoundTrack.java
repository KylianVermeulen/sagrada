package nl.avans.sagrada.model;

public class RoundTrack {
    private final int numberOfRounds = 10;
    private RoundTrackField[] roundTrackFields;

    /**
     * Full Constructor
     */
    public RoundTrack() {
        roundTrackFields = new RoundTrackField[numberOfRounds];
        makeRoundTrackFields();
    }

    /**
     * Makes the roundTrackFields
     */
    private void makeRoundTrackFields() {
        for (int i = 0; i < roundTrackFields.length; i++) {
            roundTrackFields[i] = new RoundTrackField();
        }
    }

    /**
     * Gets the roundTrackField of the given index
     *
     * @param index int
     * @return RoundTrackField
     */
    public RoundTrackField getRoundTrackField(int index) {
        return roundTrackFields[index];
    }

    /**
     * Gets the number of rounds
     *
     * @return int
     */
    public int getNumberOfRounds() {
        return this.numberOfRounds;
    }

    /**
     * Adds a die to the given round
     *
     * @param gameDie GameDie
     * @param round int
     */
    public void addGameDie(GameDie gameDie) {
        int index = gameDie.getRound() - 1;
        roundTrackFields[index].addDie(gameDie);
    }
}
