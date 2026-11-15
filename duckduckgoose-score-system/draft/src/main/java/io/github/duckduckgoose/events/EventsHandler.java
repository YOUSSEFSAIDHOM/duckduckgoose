package io.github.duckduckgoose.events;

import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;
import io.github.duckduckgoose.entities.Goose;
import io.github.duckduckgoose.items.BirdSeeds;
import io.github.duckduckgoose.ui.GameHUD;
import io.github.duckduckgoose.utils.GlobalVariables;

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

    private AbstractEvent checkSeedInRegion() {
        /**
         * Checks for seeds in the events region layer in the map
         */
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
