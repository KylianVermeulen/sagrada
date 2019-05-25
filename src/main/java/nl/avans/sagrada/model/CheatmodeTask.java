package nl.avans.sagrada.model;

import java.util.ArrayList;

public class CheatmodeTask implements Runnable {
    private ArrayList<GameDie> dice;

    public CheatmodeTask(ArrayList<GameDie> dice) {
        this.dice = dice;
    }

    @Override
    public void run() {
    }
}
