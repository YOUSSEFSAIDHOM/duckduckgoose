package io.github.duckduckgoose.effects;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Handles active effects
 */
public class EffectsHandler {
    Deque<AbstractEffect> activeEffects = new ArrayDeque<>();

    public EffectsHandler() {}

    /**
     * Add an effect to the current active effects
     *
     * @param newEffect the new effect to be added
     */
    public void addEffect(AbstractEffect newEffect) {
        activeEffects.add(newEffect);
    }

    /**
     * Check which of the active effects is still active
     *
     * @param delta the time change
     */
    public void updateEffects(float delta) {
        Iterator<AbstractEffect> effectIterator = activeEffects.iterator();
        while (effectIterator.hasNext()) {
            AbstractEffect effect = effectIterator.next();
            if (effect.isActive()) {
                effect.update(delta);
            } else {
                activeEffects.remove();
            }
        }
    }
}