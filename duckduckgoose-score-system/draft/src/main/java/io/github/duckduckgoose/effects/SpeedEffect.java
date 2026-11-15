package io.github.duckduckgoose.effects;

import io.github.duckduckgoose.utils.GlobalVariables;

/**
 * Establish the speed effect (happens after using energy drink item)
 */
public class SpeedEffect extends AbstractEffect {

    /**
     * Constructor method for the speed effect
     *
     * @param duration how long the effect lasts
     * @param efficacy the strength/changes of the effect
     */
    public SpeedEffect(float duration, float efficacy) {
        super(duration, efficacy);
    }

    /**
     * Apply the speed-up effect to the player
     */
    public void applyEffect() {

        GlobalVariables.playerSpeedMultiplier = GlobalVariables.speedEffectMultiplier;
        active = true;
    }

    /**
     * update the time the effect has left being active
     *
     * @param delta change in time
     */
    public void update(float delta) {
        if (duration > 0f) {
            duration = Math.max(0f, duration - delta);
        } else {
            onRemove();
        }
    }

    /**
     * Change the player's speed back to normal after effect is over
     */
    public void onRemove() {
        GlobalVariables.playerSpeedMultiplier = GlobalVariables.defaultSpeedMultiplier;
        finishEffect();
    }
}
