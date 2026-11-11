package io.github.duckduckgoose.effects;

public abstract class AbstractEffect {
    protected float duration;
    protected float efficacy; // 'strength' of the effect
    protected boolean active = false;

    public boolean isActive() {
        return active;
    }

    public AbstractEffect(float duration, float efficacy) {
        this.duration = duration;
        this.efficacy = efficacy;
    }

    // Functions to be overridden

    public abstract void applyEffect();

    public abstract void update(float delta);

    public abstract void onRemove();

    public void finishEffect() {
        active = false;
    }
}
