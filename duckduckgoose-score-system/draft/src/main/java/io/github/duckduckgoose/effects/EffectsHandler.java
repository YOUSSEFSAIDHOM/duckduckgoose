package io.github.duckduckgoose.effects;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class EffectsHandler {
    Deque<AbstractEffect> activeEffects = new ArrayDeque<>();

    public EffectsHandler() {}

    public void addEffect(AbstractEffect newEffect) {
        activeEffects.add(newEffect);
    }

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