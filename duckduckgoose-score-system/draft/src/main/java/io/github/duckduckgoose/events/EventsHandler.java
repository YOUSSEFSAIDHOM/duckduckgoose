package io.github.duckduckgoose.events;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.items.BirdSeeds;

public class EventsHandler {

    private final ArrayList<BirdSeeds> droppedSeeds;
    private final ArrayList<Rectangle> eventsRegion;
    private final Rectangle seedBounds = new Rectangle();

    private boolean countdownStarted = false;
    private boolean bloodMoonActive = false;
    private boolean justStarted = false;

    private float countdown = 3f;

    public EventsHandler(ArrayList<BirdSeeds> droppedSeeds,
                         ArrayList<Rectangle> eventsRegion) {
        this.droppedSeeds = droppedSeeds;
        this.eventsRegion = eventsRegion;
    }

    public void update(float delta) {

        if (bloodMoonActive) {
            return;
        }

        if (!countdownStarted) {
            if (seedInRegion()) {
                System.out.println("Event detected!!");
                countdownStarted = true;
            }
        } else {
            countdown -= delta;

            if (countdown <= 0) {
                countdown = 0;
                bloodMoonActive = true;
                justStarted = true;
            }
        }
    }

    private boolean seedInRegion() {
        /**
         * Checks for seeds in the events region layer in the map
         */

        for (BirdSeeds seed : droppedSeeds) {

            seedBounds.set(
                    seed.getX(),
                    seed.getY(),
                    0.5f,
                    0.5f
            );

            for (Rectangle region : eventsRegion) {
                if (seedBounds.overlaps(region)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isBloodMoonActive() {
        return bloodMoonActive;
    }

    public boolean hasJustStarted() {
        if (justStarted) {
            justStarted = false;
            return true;
        }

        return false;
    }

    public int getCountdown() {
        if (countdownStarted && !bloodMoonActive) {
            return (int) Math.ceil(countdown);
        }

        return 0;
    }
}
