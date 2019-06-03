package nl.avans.sagrada.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

        HashMap<HashMap<Integer, String>, LinkedHashMap<PatternCardField, ArrayList<PatternCardField>>> cheatmodeHashMap = new HashMap<>();
        for (GameDie gameDie : gameDice) {
            TreeMap<Integer, PatternCardField> bestPlacement = new TreeMap<>();
            ArrayList<PatternCardField> allPlacement = new ArrayList<>();
            HashMap<Integer, String> dieHashMap = new HashMap<>();
            dieHashMap.put(gameDie.getNumber(), gameDie.getColor());

            for (int x = 1; x <= PatternCard.CARD_SQUARES_WIDTH; x++) {
                for (int y = 1; y <= PatternCard.CARD_SQUARES_HEIGHT; y++) {
                    PatternCardField patternCardField = patternCardFields[x][y];
                    if (patternCardField.canPlaceDie(gameDie)) {
                        patternCardField.setDie(gameDie);
                        int score = player.calculateScore(true, false, patternCardFields);
                        bestPlacement.put(score, patternCardField);
                        allPlacement.add(patternCardField);
                        patternCardField.setDie(null);
                    }
                }
            }

            try {
                LinkedHashMap<PatternCardField, ArrayList<PatternCardField>> f = new LinkedHashMap<>();
                f.put(bestPlacement.lastEntry().getValue(), allPlacement);
                cheatmodeHashMap.put(dieHashMap, f);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        playerController.setCheatmodeMap(cheatmodeHashMap);
    }
}
