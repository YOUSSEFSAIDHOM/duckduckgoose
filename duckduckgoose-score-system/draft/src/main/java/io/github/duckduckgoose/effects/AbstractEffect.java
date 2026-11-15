package io.github.duckduckgoose.effects;

/**
 * Create an abstract effect to be adapted into various effects
 */
public abstract class AbstractEffect {
    protected float duration;
    protected float efficacy; // 'strength' of the effect
    protected boolean active = false;

    /**
     * Establish whether an effect is active or not.
     *
     * @return the state of the effect being active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Constructor for the abstract effect.
     *
     * @param duration how long the effect lasts
     * @param efficacy the strength/changes of the effect
     */
    public AbstractEffect(float duration, float efficacy) {
        this.duration = duration;
        this.efficacy = efficacy;
    }

    // Functions to be overridden

    public abstract void applyEffect();

    public abstract void update(float delta);

    public abstract void onRemove();

    /**
     * Deactivate the effect
     */
    public void finishEffect() {
        active = false;
    }
}
