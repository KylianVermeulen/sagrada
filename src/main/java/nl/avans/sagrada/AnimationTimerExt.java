package nl.avans.sagrada;

import javafx.animation.AnimationTimer;

public abstract class AnimationTimerExt extends AnimationTimer {
    long prevTime = 0;
    private long sleepNs = 0;

    public AnimationTimerExt(long sleepMs) {
        this.sleepNs = sleepMs * 1_000_000;
    }

    @Override
    public void handle(long now) {
        if ((now - prevTime) < sleepNs) {
            return;
        }
        prevTime = now;
        handle();
    }

    public abstract void handle();
}
