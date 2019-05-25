package nl.avans.sagrada.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import nl.avans.sagrada.controller.PlayerController;

public class CheatmodeTask implements Runnable {
    private TreeMap<Integer, HashMap<GameDie, PatternCardField>> treeMap;
    private PlayerController playerController;
    private Player player;
    private PatternCard patternCard;
    private PatternCardField[][] patternCardFields;
    private Game game;
    private ArrayList<GameDie> gameDice;

    public CheatmodeTask(PlayerController playerController) {
        this.playerController = playerController;
        this.player = this.playerController.getPlayer();
        this.patternCard = this.player.getPatternCard();
        this.patternCardFields = this.patternCard.getPatternCardFields(player);
        this.game = this.player.getGame();
        this.gameDice = this.game.getRoundDice();
    }

    @Override
    public void run() {
        for (GameDie gameDie : gameDice) {
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) {
                for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                    PatternCardField patternCardField = patternCardFields[x][y];
                    if (patternCardField.canPlaceDie(gameDie)) {
                        patternCardField.setDie(gameDie);
                        int score = player.calculateScore(true);
                        HashMap<GameDie, PatternCardField> hashMap = new HashMap<>();
                        hashMap.put(gameDie, patternCardField);
                        treeMap.put(score, hashMap);
                        patternCardField.setDie(null);
                    }
                }
            }
        }
        playerController.setTreeMapCheatmode(treeMap);
    }
}
