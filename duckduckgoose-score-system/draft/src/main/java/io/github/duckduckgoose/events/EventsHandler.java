package io.github.duckduckgoose.events;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.items.BirdSeeds;

public class EventsHandler {

    private final ArrayList<BirdSeeds> droppedSeeds;

    private final Rectangle gooseStampede;
    private final Rectangle statue;

    private boolean countdownStarted = false;
    private boolean gooseStampedeActive = false;
    private boolean justStarted = false;

    private float countdown = 3f;

    public EventsHandler(ArrayList<BirdSeeds> droppedSeeds,
                         Rectangle gooseStampede, Rectangle statue) {
        this.droppedSeeds = droppedSeeds;
        this.gooseStampede = gooseStampede;
        this.statue = statue;
    }

    public void update(float delta) {

        if (gooseStampedeActive) {
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
                gooseStampedeActive = true;
                justStarted = true;
            }
        }
    }

    private boolean seedInRegion() {
        /**
         * Checks for seeds in the events region layer in the map
         */

        for (BirdSeeds seeds : droppedSeeds) {

            if (seeds.getBounds().overlaps(gooseStampede)) {
                return true;
            }
        }

        return false;
    }

    public boolean isGooseStampedeActive() {
        return gooseStampedeActive;
    }

    public boolean hasJustStarted() {
        if (justStarted) {
            justStarted = false;
            return true;
        }

        return false;
    }

    public int getCountdown() {
        if (countdownStarted && !gooseStampedeActive) {
            return (int) Math.ceil(countdown);
        }

        return 0;
    }
}
