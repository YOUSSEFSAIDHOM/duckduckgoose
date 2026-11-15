package io.github.duckduckgoose.events;

public abstract class AbstractEvent {
    public boolean activated;

    abstract void trigger();

    public boolean isActivated() {
        return activated;
    }
}
