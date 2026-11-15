package io.github.duckduckgoose.events;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.utils.GlobalVariables;

public class EventsHandler {

    private final ArrayList<BirdSeeds> droppedSeeds;
    private final ArrayList<Goose> geese;

    private final Rectangle gooseStampedeRect;
    private final Rectangle statueRect;

    private GooseStampedeEvent gooseStampedeEvent;
    private SummonDuckEvent summonDuckEvent;

    private int countdown = GlobalVariables.eventCountDown;
    private float stepTimer = 0f;

    public EventsHandler(ArrayList<BirdSeeds> droppedSeeds, ArrayList<Goose> geese,
                         Rectangle gooseStampedeRect, Rectangle statueRect) {
        this.droppedSeeds = droppedSeeds;
        this.geese = geese;
        this.gooseStampedeRect = gooseStampedeRect;
        this.statueRect = statueRect;

        this.gooseStampedeEvent = new GooseStampedeEvent(geese, gooseStampedeRect);
        this.summonDuckEvent = new SummonDuckEvent();
    }

    public void update(float delta) {

        if (!gooseStampedeEvent.isActivated() || !summonDuckEvent.isActivated()) {
            AbstractEvent event = checkSeedInRegion();

            if (event != null) {
                eventCountDown(event, delta);
            }
        }
//        if (gooseStampedeActivated) {
//            return;
//        }
//
//        if (!countdownStarted) {
//            if (seedInRegion()) {
//                System.out.println("Event detected!!");
//                countdownStarted = true;
//            }
//        } else {
//            countdown -= delta;
//
//            if (countdown <= 0) {
//                countdown = 0;
//                gooseStampedeActivated = true;
//                justStarted = true;
//            }
//        }
    }

    private AbstractEvent checkSeedInRegion() {
        /**
         * Checks for seeds in the events region layer in the map
         */
        for (BirdSeeds seeds : droppedSeeds) {

            if (seeds.getBounds().overlaps(gooseStampedeRect) & !gooseStampedeEvent.isActivated()) {
                return new GooseStampedeEvent(geese, gooseStampedeRect);
            }

            if (seeds.getBounds().overlaps(statueRect) & !summonDuckEvent.isActivated()) {
                System.out.println("not implemented yet sorry xoxox");
            }
        } return null;
    }

    private void eventCountDown(AbstractEvent event, float delta) {
        if (countdown > 0) {
            stepTimer += delta;
            if (stepTimer >= 1f) {
                stepTimer = 0;
                countdown--;
            }
        } else {
            countdown = GlobalVariables.eventCountDown;
            event.trigger();
        }
    }
}
