package io.github.duckduckgoose.effects;

import io.github.duckduckgoose.utils.GlobalVariables;

public class SpeedEffect extends AbstractEffect {

    public SpeedEffect(float duration, float efficacy) {
        super(duration, efficacy);
    }

    public void applyEffect() {

        GlobalVariables.playerSpeedMultiplier = GlobalVariables.speedEffectMultiplier;
        active = true;
    }

    public void update(float delta) {
        if (duration > 0f) {
            duration = Math.max(0f, duration - delta);
        } else {
            onRemove();
        }
    }

    public void onRemove() {
        GlobalVariables.playerSpeedMultiplier = GlobalVariables.defaultSpeedMultiplier;
        finishEffect();
    }
}
