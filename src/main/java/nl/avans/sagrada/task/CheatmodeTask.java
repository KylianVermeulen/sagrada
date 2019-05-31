package nl.avans.sagrada.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import nl.avans.sagrada.controller.PlayerController;
import nl.avans.sagrada.model.Game;
import nl.avans.sagrada.model.GameDie;
import nl.avans.sagrada.model.PatternCard;
import nl.avans.sagrada.model.PatternCardField;
import nl.avans.sagrada.model.Player;

public class CheatmodeTask implements Runnable {
    private PlayerController playerController;
    private Player player;
    private PatternCard patternCard;
    private PatternCardField[][] patternCardFields;
    private Game game;
    private ArrayList<GameDie> gameDice;

    public CheatmodeTask(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void run() {
        player = playerController.getPlayer();
        patternCard = player.getPatternCard();
        patternCardFields = patternCard.getPatternCardFields(player);
        game = player.getGame();
        gameDice = game.getRoundDice();
        HashMap<HashMap<Integer, String>, TreeMap<Integer, PatternCardField>> treeMapHashMap = new HashMap<>();
        for (GameDie gameDie : gameDice) {
            TreeMap<Integer, PatternCardField> treeMap = new TreeMap<>();
            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) {
                for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                    PatternCardField patternCardField = patternCardFields[x][y];
                    if (patternCardField.canPlaceDie(gameDie)) {
                        patternCardField.setDie(gameDie);
                        int score = player.calculateScore(true, false);
                        treeMap.put(score, patternCardField);
                        patternCardField.setDie(null);
                    }
                }
            }
            HashMap<Integer, String> hashMap = new HashMap<>();
            hashMap.put(gameDie.getNumber(), gameDie.getColor());
            treeMapHashMap.put(hashMap, treeMap);
        }
        playerController.setTreeMapHashMap(treeMapHashMap);
    }
}
