
package com;

public class EnergyDrink {

    public static final float SPEED_MULTIPLIER = 1.5f;  // +50% speed while active
    public static final float DURATION = 5f;            // seconds

    private float timeLeft = 0f;

    public void use() {
        timeLeft = DURATION;
    }

    public void update(float delta) {
        if (timeLeft > 0f) {
            timeLeft = Math.max(0f, timeLeft - delta);
        }
    }

    public boolean isActive() {
        return timeLeft > 0f;
    }

    public float getSpeedMultiplier() {
        if(isActive()){
            return SPEED_MULTIPLIER;
        } else{
            return 1f;
        }
    }

    public float getTimeLeft() {
        return timeLeft;
    }

}
