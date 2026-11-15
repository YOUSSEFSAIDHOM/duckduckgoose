package io.github.duckduckgoose.events;

import io.github.duckduckgoose.utils.GlobalVariables;

public abstract class AbstractEvent {
    public boolean activated;
    public String eventName;

    private int countdown = GlobalVariables.eventCountDown;
    private float stepTimer = 0f;

    abstract void trigger();

    public boolean isActivated() {
        return activated;
    }

    public int getCountdown() {
        return countdown;
    }

    public String getName() {
        return eventName;
    }

    public boolean countDownFinished(float delta) {
        /**
         * Creates a count down before the event itself is triggerred.
         * @param delta is used to reference in-game delta time the countdown is accurate to gameplay.
         * @returns true once the event has been triggerred
         */

        if (countdown > 0) {
            stepTimer += delta;
            if (stepTimer >= 1f) {
                stepTimer -= 1f;
                countdown--;
            }
            return false; // Because event hasn't been triggerred yet
        }
        // Now count down is 0 so we can trigger the event and return true
        trigger();
        return true;
    }
}
