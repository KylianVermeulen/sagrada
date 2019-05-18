package nl.avans.sagrada.model.toolcard;

import nl.avans.sagrada.controller.PlayerController;

public class ToolCardThreadHandler implements Runnable {
    private PlayerController playerController;
    
    public ToolCardThreadHandler(PlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void run() {
        while (true) {
            allowPlacement();
        }
    }
    
    private void allowPlacement(ToolCard toolCard) {
        if (toolCard instanceof ToolCardGlasBreekTang) {
            for (int i = 0; i < 2; i++) {
                playerController.actionPlaceDie(patternCard, patternCardField, gameDie, event); 
            }
        } else {
            playerController.actionPlaceDie(patternCard, patternCardField, gameDie, event);
        }
        try {
            Thread.sleep(10000); // tijdelijk
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
