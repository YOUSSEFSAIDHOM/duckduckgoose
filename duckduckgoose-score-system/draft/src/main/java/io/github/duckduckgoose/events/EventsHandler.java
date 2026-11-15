package io.github.duckduckgoose.events;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.ui.GameHUD;
import io.github.duckduckgoose.utils.GlobalVariables;

/**
 * Handles the state and duration of hidden events
 */
public class EventsHandler {

    private final ArrayList<BirdSeeds> droppedSeeds;
    private final ArrayList<Goose> geese;

    private final Rectangle gooseStampedeRect;
    private final Rectangle statueRect;

    private GooseStampedeEvent gooseStampedeEvent;
    private SummonDuckEvent summonDuckEvent;
    private AbstractEvent currentEvent;

    // We'll need an instance of gameHud so that you can show the 3 second countdown before an event starts
    private GameHUD gameHud;

    private int countdown = GlobalVariables.eventCountDown;
    private float stepTimer = 0f;

    /**
     * Constructor method for events handler
     *
     * @param droppedSeeds an array of all the seeds active on the map
     * @param gooseStampedeRect the area where the goose Stampede can be activated
     * @param statueRect the area where the statue can be activated
     */
    public EventsHandler(ArrayList<BirdSeeds> droppedSeeds, ArrayList<Goose> geese,
                         Rectangle gooseStampedeRect, Rectangle statueRect, GameHUD gameHud) {
        this.droppedSeeds = droppedSeeds;
        this.geese = geese;
        this.gooseStampedeRect = gooseStampedeRect;
        this.statueRect = statueRect;
        this.gameHud = gameHud;

        this.gooseStampedeEvent = new GooseStampedeEvent(geese, gooseStampedeRect);
        this.summonDuckEvent = new SummonDuckEvent(geese, statueRect);
    }

    /**
     * Update whether the events are still active and update cooldown
     *
     * @param delta change in time
     */
    public void update(float delta) {

        if (!gooseStampedeEvent.isActivated() || !summonDuckEvent.isActivated()) {

            if (currentEvent != null && !currentEvent.isActivated()) {
               // Start countdown and show the timer
                if (!currentEvent.countDownFinished(delta)) {
                    gameHud.showEventCountdown(currentEvent.getName() ,currentEvent.getCountdown());
                } else {
                    gameHud.hideEventCountdown();
                }
                return;
            }

            currentEvent = checkSeedInRegion();
        }
        gameHud.hideEventCountdown();
    }

    /**
     * Check whether seed has been dropped in an area to trigger an event
     *
     * @return the state of a seed being in the event region or not
     */
    private AbstractEvent checkSeedInRegion() {
        for (BirdSeeds seeds : droppedSeeds) {

            if (seeds.getBounds().overlaps(gooseStampedeRect) & !gooseStampedeEvent.isActivated()) {
                return gooseStampedeEvent;
            }

            if (seeds.getBounds().overlaps(statueRect) & !summonDuckEvent.isActivated()) {
                return summonDuckEvent;
            }
        } return null;
    }
}
